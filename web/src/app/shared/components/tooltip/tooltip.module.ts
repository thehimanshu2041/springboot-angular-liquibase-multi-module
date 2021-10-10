import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TooltipComponent } from './tooltip.component';


/**
 * @internal
 */

@NgModule({
  declarations: [TooltipComponent],
  imports: [CommonModule],
  exports: [TooltipComponent]
})
export class TooltipModule { }

export { TooltipComponent };
