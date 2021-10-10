import {
  Component,
  ChangeDetectorRef,
  OnDestroy,
  ElementRef,
  ViewChild,
  AfterViewInit,
  OnInit
} from '@angular/core';
import { MediaMatcher } from '@angular/cdk/layout';
import { ColorScheme } from '../../shared/models/shared.model';
import { BaseContainerComponent } from './base.container.component';
import { MenuService } from 'src/app/shared';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'ts-trisul-container-component',
  templateUrl: './trisul.container.component.html',
  styleUrls: ['./trisul.container.component.scss']
})
export class NavBarComponent extends BaseContainerComponent implements OnInit, OnDestroy, AfterViewInit {

  mobileQuery: MediaQueryList;

  @ViewChild('appDrawer', { static: true })
  appDrawer: ElementRef;
  _mobileQueryListener: () => void;

  matToggleBoolean = false;
  colorScheme: ColorScheme;

  constructor(
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    menuService: MenuService,
    authService: AuthService,
    router: Router) {
    super(menuService, authService, router);
    this.mobileQuery = media.matchMedia('(max-width: 960px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
    document.querySelector('html').classList.add('trisul');
  }

  ngOnInit(): void {
    super.ngOnInit();
  }

  ngOnDestroy(): void {
    super.ngOnDestroy();
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  ngAfterViewInit() {
    this.menuService.appDrawer = this.appDrawer;
  }

}
