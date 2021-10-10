import { Component, Input, ViewChild } from '@angular/core';
import { MenuDetail } from 'src/app/generated/rest';


@Component({
  selector: 'ts-horizontal-menu',
  templateUrl: './horizontal.menu.component.html',
  styleUrls: ['./horizontal.menu.component.scss']
})
export class HorizontalMenuComponent {
  @Input() menuItems: MenuDetail[];
  @ViewChild('childMenu', { static: false }) public childMenu;
}
