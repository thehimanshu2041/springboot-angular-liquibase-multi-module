import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AlertAreaComponent } from './alert.area.component';


/**
 * @internal
 */

@NgModule({
  declarations: [AlertAreaComponent],
  imports: [CommonModule],
  exports: [AlertAreaComponent]
})
export class AlertAreaModule { }

export { AlertAreaComponent };
