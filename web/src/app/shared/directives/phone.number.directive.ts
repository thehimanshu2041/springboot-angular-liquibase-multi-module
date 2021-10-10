import {
  Directive,
  ElementRef,
  HostListener,
  Injector,
  Input,
  OnDestroy,
  OnInit,
  Optional,
  StaticProvider,
  forwardRef
} from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormControlDirective,
  FormControlName,
  NG_VALIDATORS,
  NgModel,
  Validator
} from '@angular/forms';
import * as intlTelInput from 'intl-tel-input';
import * as _ from 'lodash';


type TEL_TYPE = 'MOBILE' | 'FIXED_LINE_OR_MOBILE' | 'FIXED_LINE';

const SELECTOR =
  '[tsTelInput], input([type=tel])[ngModel], input([type=tel])[formControlName], input([type=tel])[formControl]';

export const PHONE_VALIDATOR: StaticProvider = {
  provide: NG_VALIDATORS,
  useExisting: forwardRef(() => PhoneNumberValidator),
  multi: true
};

declare const intlTelInputUtils: any;

@Directive({
  selector: SELECTOR,
  providers: [PHONE_VALIDATOR]
})
export class PhoneNumberValidator implements Validator {
  @Input()
  telType: TEL_TYPE = 'FIXED_LINE';

  static validateControl(
    c: AbstractControl,
    inp: HTMLInputElement,
    type: TEL_TYPE,
    update: boolean
  ): { [key: string]: any } {
    const err = { tel: true };
    if (!inp.value) {
      return;
    }
    const iti = (inp as any).$$iti;
    if (!iti) {
      return;
    }
    if (iti.isValidNumber()) {
      const n = iti.getNumber(intlTelInputUtils.numberFormat.INTERNATIONAL);
      if (update) {
        inp.value = n;
      }

      const t = iti.getNumberType();
      if (t !== intlTelInputUtils.numberType.FIXED_LINE_OR_MOBILE) {
        if (
          (type === 'MOBILE' && t !== intlTelInputUtils.numberType.MOBILE) ||
          (type === 'FIXED_LINE' && t !== intlTelInputUtils.numberType.FIXED_LINE)
        ) {
          return err;
        }
      }
    } else {
      return err;
    }
  }

  constructor(private _el: ElementRef) { }

  validate(c: AbstractControl): { [key: string]: any } {
    return PhoneNumberValidator.validateControl(c, this._el.nativeElement, this.telType, false);
  }
}

@Directive({
  selector: SELECTOR,
  exportAs: 'tsTelInput'
})
export class PhoneNumberDirective implements OnInit, OnDestroy {
  @Input()
  telType: TEL_TYPE = 'FIXED_LINE';

  @Input()
  telDropdown = true;

  private _control: FormControl;
  private _reactive: boolean;
  private _countryChangeListener: () => void;
  private _input: HTMLInputElement;
  private _iti: any;

  constructor(
    el: ElementRef,
    @Optional() private _ngModel: NgModel,
    @Optional() private _formControlName: FormControlName,
    @Optional() private _formControlDirective: FormControlDirective,
    private _injector: Injector
  ) {
    this._input = el.nativeElement;
  }

  @HostListener('input')
  onInput() {
    const err = PhoneNumberValidator.validateControl(this._control, this._input, this.telType, true);
    if (this._reactive && err) {
      this._control.setErrors(_.merge(err, this._control.errors));
    }
  }

  ngOnInit() {
    this._reactive = this._ngModel ? false : true;
    const ctrDirective = this._ngModel || this._formControlName || this._formControlDirective;
    this._control = ctrDirective.control as FormControl;

    this._iti = intlTelInput(this._input, {
      initialCountry: 'in',
      placeholderNumberType: this.telType,
      preferredCountries: _.uniq(['in', 'au', 'ca', 'nz', 'us', 'gb']),
      dropdownContainer: document.body,
      autoPlaceholder: false,
      allowDropdown: this.telDropdown
    });
    this.initElement();
  }

  ngOnDestroy() {
    if (this._countryChangeListener) {
      this._input.removeEventListener('countrychange', this._countryChangeListener);
    }
    this._iti.destroy();
  }

  private initElement() {
    (this._input as any).$$iti = this._iti;
    this._input.addEventListener('countrychange', this._countryChangeListener);
    window.requestAnimationFrame(() => {
      let field: HTMLElement = this._input.parentElement;
      let n = 0;
      while (!field.classList.contains('mat-form-field-infix') && n++ < 3) {
        field = field.parentElement;
      }
      const label = field.querySelector('.mat-form-field-label');
      if (label && this.telDropdown) {
        label.classList.add('ts-tel-input-placeholder');
      }

      if (this._input.value) {
        this._input.classList.add('ts-tel-input-with-value');
      }
    });

    this._input.addEventListener('click', () => {
      if (this._control.disabled && this._control.value) {
        window.location.href = 'tel://' + this._control.value;
      }
    });

    this._control.registerOnDisabledChange(isDisabled => {
      setTimeout(() => {
        if (!isDisabled) {
          this.onInput();
        } else {
          const v = this._iti.getNumber(intlTelInputUtils.numberFormat.NATIONAL);
          this._input.value = v;
          this._control.patchValue(this._input.value, { emitEvent: false });
        }
      }, 20);
    });
  }
}
