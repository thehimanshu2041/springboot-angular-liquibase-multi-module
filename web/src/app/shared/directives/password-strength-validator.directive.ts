import { Directive, forwardRef } from '@angular/core';
import { AbstractControl, AsyncValidator, NG_ASYNC_VALIDATORS, ValidationErrors } from '@angular/forms';

@Directive({
  selector: '[advPasswordStrength][formControlName],[advPasswordStrength][formControl],[advPasswordStrength][ngModel]',
  providers: [
    {
      provide: NG_ASYNC_VALIDATORS,
      useExisting: forwardRef(() => PasswordStrengthValidatorDirective),
      multi: true
    }
  ]
})
export class PasswordStrengthValidatorDirective implements AsyncValidator {
  validate(ctrl: AbstractControl): Promise<ValidationErrors> {
    return validatePasswordStrength(ctrl);
  }
}

export async function validatePasswordStrength(ctrl: AbstractControl): Promise<ValidationErrors> {
  if (ctrl && ctrl.value) {
    const result = await getValidationResult(ctrl.value);
    if (result.score < 3) {
      return { weakPassword: true };
    }
  }
  return null;
}

export async function getValidationResult(password: string): Promise<any> {
  const zxcvbn = (await import('zxcvbn')).default;
  const result = zxcvbn(password);
  if (result.score < 2) {
    return result;
  } else {
    if (
      password.match(/.*[a-z].*/) &&
      password.match(/.*[A-Z].*/) &&
      password.match(/.*\d.*/) &&
      password.match(/.*[^a-zA-Z\d]/)
    ) {
      return result;
    } else {
      result.score = 1;
      result.feedback = {
        warning:
          'Password must contain at list a uppercase character, a lowercase character, a digit, and a special character.'
      };
    }
  }
  return result;
}
