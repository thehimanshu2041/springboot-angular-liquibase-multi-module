import { Component, Input } from '@angular/core';

@Component({
  selector: 'ts-tooltip',
  templateUrl: './tooltip.component.html',
  styleUrls: ['./tooltip.component.scss']
})
export class TooltipComponent {
  @Input() content: string = '';

  tooltipSize = '';
}
