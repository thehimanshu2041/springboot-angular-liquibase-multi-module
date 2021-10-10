import { Component, Input } from '@angular/core';
import { MenuDetail } from 'src/app/generated/rest';

@Component({
  selector: 'ts-horizontal-main-menu',
  templateUrl: './horizontal.main.menu.component.html',
  styleUrls: ['./horizontal.main.menu.component.scss']
})
export class HorizontalMainMenuComponent {
  @Input() menuItems: MenuDetail[];
}
