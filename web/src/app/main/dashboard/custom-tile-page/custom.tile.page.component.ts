import { animate, style, transition, trigger } from '@angular/animations';
import {
  Component,
  ComponentFactoryResolver,
  ElementRef,
  Injector,
  Input,
  QueryList,
  TemplateRef,
  Type,
  ValueProvider,
  ViewChild,
  ViewChildren,
  ViewContainerRef,
  OnInit,
  OnDestroy,
  AfterViewInit
} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import * as _ from 'lodash';
import { Observable, Subject, Subscription, timer } from 'rxjs';
import { delay, filter, flatMap, map, tap } from 'rxjs/operators';
import { TILE_SPECS, CONTENT_TILE_CONTEXT, ContentTileContext, ContentTileEvent } from './tile-config/tile.modal';

import { contentTileRegistry } from './tile-config/content.decorators';
import { ToBeImplementedComponent } from './to.be.implemented.component';
import { ManagedContent, StaticDataServiceService } from 'src/app/generated/rest';
import { ThemeService } from 'src/app/shared';


interface ManagedContentWithCtx extends ManagedContent {
  context?: ContentTileContext;
}

@Component({
  selector: 'app-custom-tile-page-component',
  templateUrl: './custom.tile.page.component.html',
  styleUrls: ['./custom.tile.page.component.scss'],
  animations: [
    trigger('zoomInOut', [
      transition(':enter', [style({ transform: 'scale(0.7)' }), animate('.25s')]),
      transition(':leave', [animate('.25s', style({ transform: 'scale(0.7)' }))])
    ])
  ]
})
export class CustomTilePageComponent implements OnInit, OnDestroy {

  contents: ManagedContentWithCtx[];
  deletedContents: ManagedContentWithCtx[] = [];
  contentActions: Function[] = [];
  editing = false;
  tileSpecs = TILE_SPECS;
  reconfigurable: boolean;

  @Input()
  asCards = true;

  @Input()
  singleColumnMode: boolean;

  @Input()
  editButtonInHeader = true;

  @Input()
  heading: string;

  @ViewChildren('contentItemContainer', { read: ViewContainerRef })
  private itemContainers: QueryList<ViewContainerRef>;

  @ViewChildren('contentActionContainer', { read: ViewContainerRef })
  private actionContainers: QueryList<ViewContainerRef>;

  @ViewChildren('contentFooterContainer', { read: ViewContainerRef })
  private footerContainers: QueryList<ViewContainerRef>;

  @ViewChildren('contentOverlayContainer', { read: ViewContainerRef })
  private overlayContainers: QueryList<ViewContainerRef>;

  @ViewChild('contentItemHtml', { static: true })
  private contentHtmlTemplate: TemplateRef<any>;

  @Input()
  private contentKey: string;

  private _eventBus: Subject<ContentTileEvent> = new Subject();
  private _savingSubs: Subscription;
  private _contentLoading: Subscription;

  constructor(
    private _componentFactoryResolver: ComponentFactoryResolver,
    private staticDataSevrice: StaticDataServiceService,
    private _domSanitizer: DomSanitizer,
    private _dialogService: MatDialog,
    private themeService: ThemeService
  ) { }

  ngOnInit(): void {
    console.log(this.itemContainers);
    this._contentLoading = this.staticDataSevrice.getConfiguredTile()
      .pipe(tap(cArray => (this.contents = cArray || [])),
        delay(100))
      .subscribe(cArray =>
        (cArray || []).forEach((mc, i) => {
          if (mc.tile && mc.tile.key) {
            if (this.singleColumnMode) {
              mc.tile.type = 'C11';
            }
            this.createTile(mc.tile.key, mc.tile.type, i);
          } else {
            this.createHtml(mc.html, i);
          }
        })
      );
  }

  ngOnDestroy(): void {
    this._eventBus.complete();
  }

  get contentLoading(): boolean {
    return this._contentLoading && !this._contentLoading.closed;
  }

  async renderAction(templ: TemplateRef<any>, index: number) {
    const actionVC = this.actionContainers.toArray()[index];
    if (!actionVC) {
      return;
    }
    const embeddedRef = actionVC.createEmbeddedView(templ);
    await timer(0).toPromise();
    const nodes: HTMLElement[] = embeddedRef.rootNodes;
    const firstButton = this.findActionButton(nodes);
    if (firstButton) {
      this.contentActions[index] = firstButton.click.bind(firstButton);
    }
  }

  renderFooter(templ: TemplateRef<any>, index: number) {
    const vc = this.footerContainers.toArray()[index];
    if (!vc) {
      return;
    }
    vc.createEmbeddedView(templ);
  }

  renderOverlay(templ: TemplateRef<any>, index: number) {
    const vc = this.overlayContainers.toArray()[index];
    if (!vc) {
      return;
    }
    vc.createEmbeddedView(templ);
  }

  headerClicked(index: number) {
    const ctx = this.getTileContext(index);
    if (ctx.expandable) {
      ctx.expanded = !ctx.expanded;
    } else if (this.contentActions[index]) {
      this.contentActions[index]();
    }
  }

  getTileContext(index: number) {
    return this.contents[index].context;
  }

  footerContentChanged(footerIdx: number) {
    this._showHideViewContByChildCount(this.footerContainers.toArray()[footerIdx]);
  }

  overlayContentChanged(idx: number) {
    this._showHideViewContByChildCount(this.overlayContainers.toArray()[idx]);
  }

  toggleEditing() {
    if (this.editing) {
      const tempTileData = this.contents.map(ci => ({ tile: ci.tile, html: ci.html }));
      this.deletedContents.forEach(t => tempTileData.push({ tile: t.tile, html: t.html }));

      console.log(tempTileData);
      // tempTileData.forEach(data => {
      //     this.staticDataSevrice.saveTileConfiguration(data.tile).subscribe();
      // });
      this.editing = false;
    } else {
      this.editing = true;
    }
  }

  setTileSize(index: number, spec: [number, number]) {
    const item = this.contents[index];
    item.tile.type = this.toCellString(spec);
    this.itemContainers.toArray()[index].clear();
    this.actionContainers.toArray()[index].clear();
    this.footerContainers.toArray()[index].clear();
    this.overlayContainers.toArray()[index].clear();
    this.createTile(item.tile.key, item.tile.type, index);
  }

  deleteTile(index: number) {
    this.contents.forEach((t, i) => {
      if (i === index) {
        t.tile.isAccessed = false;
        this.deletedContents.push(t);
      }
    });

    this.deletedContents = _.uniqWith(this.deletedContents, _.isEqual);
    this.contents = this.contents.filter((t, i) => i !== index);
  }

  toCellString(spec: [number, number]) {
    return `C${spec[0]}${spec[1] || ''}`;
  }

  get isNightMode(): boolean {
    return this.themeService._isNightMode();
  }

  get saving(): boolean {
    return this._savingSubs && !this._savingSubs.closed;
  }

  private _showHideViewContByChildCount(vc: ViewContainerRef) {
    if (vc) {
      const el: HTMLElement = vc.element.nativeElement.parentElement;
      if (el.childElementCount) {
        el.classList.remove('d-none');
      } else {
        el.classList.add('d-none');
      }
    }
  }

  private findActionButton(nodes: HTMLElement[]) {
    let b: HTMLButtonElement = nodes.find(n => n.nodeName === 'BUTTON') as HTMLButtonElement;
    if (b && !b.classList.contains('mat-button-toggle-button')) {
      return b;
    }
    for (const node of nodes) {
      b = this.findActionButton(Array.from(node.children || []) as HTMLElement[]);
      if (b) {
        return b;
      }
    }
  }

  private createTile(tileKey: string, type: string, index: number): HTMLElement {
    let component: Type<any>;
    const regItem = contentTileRegistry[tileKey];
    if (regItem) {
      component = regItem.__class;
    }
    component = component || ToBeImplementedComponent;
    const factory = this._componentFactoryResolver.resolveComponentFactory(component);
    const ctx = {
      key: tileKey,
      index,
      type,
      expanded: true,
      framed: true,
      title: this.contents[index].tile.title,
      allTiles: this.contents.filter(c => c.tile).map(c => c.tile.key),
      fireEvent: (tile, event) => this._fireEvent(tile, event),
      watchEvents: () => this._watchEvents(tileKey)
    };
    this.contents[index].context = ctx;
    const contextProvider: ValueProvider = {
      provide: CONTENT_TILE_CONTEXT,
      useValue: ctx
    };
    const childVC = this.itemContainers.toArray()[index];
    const newInjector = Injector.create([contextProvider], childVC?.injector);
    const compRef = childVC?.createComponent(factory, undefined, newInjector);
    const elRef: ElementRef<HTMLElement> = compRef?.injector.get(ElementRef);
    elRef?.nativeElement.classList.add('content-item-body', 'flex-grow-1');
    return elRef.nativeElement;
  }

  private createHtml(html: string, index: number) {
    const childVC = this.itemContainers.toArray()[index];
    const cls = (/\bcontent-item-c[1-3][1-3]?\b/.exec(html || '') || [])[0] || 'content-item-c3';
    const targetHtml = this._domSanitizer.bypassSecurityTrustHtml(html.replace(cls, ''));
    const elem: HTMLElement = childVC.createEmbeddedView(this.contentHtmlTemplate, { $implicit: targetHtml })
      .rootNodes[0];
    elem.parentElement.classList.add(cls);
  }

  private _fireEvent(tikeKey: string, event: string): Promise<any> {
    let resultResolver: (value: any) => void;
    const resultSubj = new Promise(resolve => {
      resultResolver = resolve;
    });
    const ev = new ContentTileEvent(tikeKey, event, resultResolver);
    this._eventBus.next(ev);
    return resultSubj;
  }

  private _watchEvents(tileKey: string): Observable<ContentTileEvent> {
    return this._eventBus.pipe(filter(ev => ev.tileKey === tileKey));
  }

}

// import {
//     Component,
//     ComponentFactoryResolver,
//     ElementRef,
//     Injector,
//     Input,
//     OnDestroy,
//     OnInit,
//     QueryList,
//     TemplateRef,
//     Type,
//     ValueProvider,
//     ViewChild,
//     ViewChildren,
//     ViewContainerRef,
//     InjectionToken
// } from '@angular/core';
// import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
// import { Router } from '@angular/router';
// import { Observable, Subject, Subscription, timer } from 'rxjs';
// import { delay, filter, flatMap, map, tap } from 'rxjs/operators';
// import { DomSanitizer } from '@angular/platform-browser';
// import { predefinedAnimations } from 'src/app/common/ui/predefined.animations';
// import { contentTileRegistry } from './tile-config/content.decorators';
// import { ManagedContentWithCtx, ContentTileEvent, CONTENT_TILE_CONTEXT } from './tile-config/tile.modal';
// import { publicService } from 'src/app/common/service/nav.service';
// import { trigger, transition, style, animate } from '@angular/animations';
// import { ThemeService } from 'src/app/common/service/theme.service';

// @Component({
//     selector: 'app-custom-tile-page-component',
//     templateUrl: './custom.tile.page.component.html',
//     styleUrls: ['./custom.tile.page.component.scss'],
//     animations: [
//         trigger('zoomInOut', [
//             transition(':enter', [style({ transform: 'scale(0.7)' }), animate('.25s')]),
//             transition(':leave', [animate('.25s', style({ transform: 'scale(0.7)' }))])
//         ])
//     ]
// })
// export class CustomTilePageComponent implements OnInit, OnDestroy {
//     contents: ManagedContentWithCtx[];
//     contentActions: Function[] = [];

//     @Input('accesstype') accesstype: string;

//     @ViewChildren('contentItemContainer', { read: ViewContainerRef })
//     private itemContainers: QueryList<ViewContainerRef>;

//     @ViewChildren('contentActionContainer', { read: ViewContainerRef })
//     private actionContainers: QueryList<ViewContainerRef>;

//     private _eventBus: Subject<ContentTileEvent> = new Subject();

//     private loading: Subscription;

//     constructor(private router: Router, private publicService: publicService, private themeService: ThemeService,
//         private _componentFactoryResolver: ComponentFactoryResolver, private _domSanitizer: DomSanitizer) {

//     }

//     ngOnInit(): void {
//         this.loading = this.publicService.getTileList(this.accesstype)
//             .pipe(tap(cArray => (this.contents = cArray.data || [])),
//                 delay(100))
//             .subscribe(data => {
//                 if (data.status) {
//                     data.data.forEach((mc, i) => {
//                         this.createTile(mc.tile.key, mc.tile.type, i);
//                     })
//                 }
//             });
//     }

//     get isNightMode(): boolean {
//      return this.themeService._isNightMode();
//     }

//     ngOnDestroy() {
//         if (this.loading) {
//             this.loading.unsubscribe();
//         }
//     }

//     private createTile(tileKey: string, type: string, index: number): HTMLElement {
//         let component: Type<any>;
//         const regItem = contentTileRegistry[tileKey];
//         if (regItem) {
//             component = regItem.__class;
//         }
//         component = component;
//         const factory = this._componentFactoryResolver.resolveComponentFactory(component);
//         const ctx = {
//             key: tileKey,
//             index,
//             type,
//             expanded: true,
//             framed: true,
//             title: this.contents[index].tile.title,
//             allTiles: this.contents.filter(c => c.tile).map(c => c.tile.key),
//             fireEvent: (tile, event) => this._fireEvent(tile, event),
//             watchEvents: () => this._watchEvents(tileKey)
//         };
//         this.contents[index].context = ctx;
//         const contextProvider: ValueProvider = {
//             provide: CONTENT_TILE_CONTEXT,
//             useValue: ctx
//         };
//         const childVC = this.itemContainers.toArray()[index];
//         const newInjector = Injector.create([contextProvider], childVC.injector);
//         const compRef = childVC.createComponent(factory, undefined, newInjector);
//         const elRef: ElementRef = compRef.injector.get(ElementRef);
//         elRef.nativeElement.classList.add('content-item-body', 'flex-grow-1');
//         return elRef.nativeElement;
//     }

//     private _fireEvent(tikeKey: string, event: string): Promise<any> {
//         let resultResolver: (value: any) => void;
//         const resultSubj = new Promise(resolve => {
//             resultResolver = resolve;
//         });
//         const ev = new ContentTileEvent(tikeKey, event, resultResolver);
//         this._eventBus.next(ev);
//         return resultSubj;
//     }

//     private _watchEvents(tileKey: string): Observable<ContentTileEvent> {
//         return this._eventBus.pipe(filter(ev => ev.tileKey === tileKey));
//     }
//     getTileContext(index: number) {
//         return this.contents[index].context;
//     }
// }



