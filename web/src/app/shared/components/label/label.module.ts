import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';

import { LabelComponent } from './label.component';

/**
 * @internal
 */

@NgModule({
	declarations: [LabelComponent],
	imports: [CommonModule, MatFormFieldModule],
	exports: [LabelComponent]
})
export class LabelModule {}

export { LabelComponent };
