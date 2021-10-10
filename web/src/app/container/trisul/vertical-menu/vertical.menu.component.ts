import { Component, HostBinding, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MenuDetail } from 'src/app/generated/rest';
import { MenuService } from 'src/app/shared';

@Component({
  selector: 'ts-vertical-menu-component',
  templateUrl: './vertical.menu.component.html',
  styleUrls: ['./vertical.menu.component.scss'],
  animations: [
    trigger('indicatorRotate', [
      state('collapsed', style({ transform: 'rotate(0deg)' })),
      state('expanded', style({ transform: 'rotate(180deg)' })),
      transition('expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4,0.0,0.2,1)')
      ),
    ])
  ]
})
export class VerticalMenuComponent implements OnInit {
  expanded: boolean = false;
  @HostBinding('attr.aria-expanded') ariaExpanded = this.expanded;
  @Input() item: MenuDetail;
  @Input() depth: number;

  constructor(public menuService: MenuService,
    public router: Router) {
    if (this.depth === undefined) {
      this.depth = 0;
    }
  }

  ngOnInit() {
    this.menuService.currentUrl.subscribe((url: string) => {
      if (this.item.menuPath && url) {
        this.expanded = url.indexOf(`/${this.item.menuPath}`) === 0;
        this.ariaExpanded = this.expanded;
      }
    });
  }

  onItemSelected(item: MenuDetail) {
    if (!item.children || !item.children.length) {
      this.router.navigate([item.menuPath]);
      this.menuService.closeNav();
    }
    if (item.children && item.children.length) {
      this.expanded = !this.expanded;
    }
  }
}
