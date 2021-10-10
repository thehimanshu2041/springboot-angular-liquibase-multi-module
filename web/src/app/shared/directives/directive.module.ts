import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AutoFocusDirective } from './auto-focus.directive';
import { PasswordStrengthValidatorDirective } from './password-strength-validator.directive';
import { PhoneNumberDirective, PhoneNumberValidator } from './phone.number.directive';
import { TooltipDirective } from './tooltip.directive';


/**
 * @internal
 */

const EXPORTED_COMPONENTS = [
  TooltipDirective,
  AutoFocusDirective,
  PhoneNumberDirective,
  PhoneNumberValidator,
  PasswordStrengthValidatorDirective
];


@NgModule({
  declarations: [EXPORTED_COMPONENTS],
  imports: [
    CommonModule
  ],
  exports: [...EXPORTED_COMPONENTS]
})
export class DirectiveModule { }

