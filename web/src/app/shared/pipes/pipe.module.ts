import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { PhoneNumberPipe } from './phone.number.pipe';
import { UserCurrencyPipe } from './user.currency.pipe';
import { WorkingPipe } from './working.pipe';


/**
 * @internal
 */

const EXPORTED_COMPONENTS = [
  UserCurrencyPipe,
  PhoneNumberPipe,
  WorkingPipe
];


@NgModule({
  declarations: [EXPORTED_COMPONENTS],
  imports: [
    CommonModule
  ],
  exports: [...EXPORTED_COMPONENTS]
})
export class PipeModule { }

