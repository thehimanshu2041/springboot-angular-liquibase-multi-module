import { Component, ElementRef, HostBinding, Injector, EventEmitter, Input, OnInit, ViewChild, forwardRef, OnDestroy, Output } from '@angular/core';
import {
  AbstractControl,
  ControlValueAccessor,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
  NgForm,
  ValidationErrors,
  Validator
} from '@angular/forms';

@Component({
  selector: 'adv-credit-card',
  templateUrl: './card-detail.component.html',
  styleUrls: ['./card-detail.component.scss'],
  providers: [
    { provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => CardDetailComponent), multi: true },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => CardDetailComponent),
      multi: true
    }
  ]
})
export class CardDetailComponent implements OnInit, ControlValueAccessor, Validator {
  _disabled: boolean;
  _value: CreditCardInfo = {};
  _initialized: boolean;
  _invalidCardNumber: boolean;
  @Input() hideRequiredMarker = false;
  success: string;
  error: string;
  creditCardInfo: CreditCardInfo;

  @HostBinding('class')
  get classes(): string {
    return 'adv-credit-card';
  }

  @ViewChild('expiryMonth', { static: false }) private _shadowExpiryMonthInput: ElementRef<HTMLInputElement>;
  @ViewChild('cardForm', { static: false }) private _cardForm: NgForm;

  private _cardTypeFn: any;
  private _changeFn: (value: CreditCardInfo) => void;
  private _touchFn: () => void;
  private _validatorChangeFn: () => void;

  async ngOnInit() {
    await import('card/dist/card');

    this._cardTypeFn = (await import('credit-card-type')).default;

    this._initialized = true;
  }

  writeValue(obj: any): void {
    this._value = obj || {};
  }

  setDisabledState(isDisabled: boolean): void {
    this._disabled = isDisabled;
  }

  validate(control?: AbstractControl): ValidationErrors {
    this._invalidCardNumber = false;
    let invalidInfo: boolean;
    if (this._initialized && !this._disabled && this._cardForm) {
      this._cardForm.control.updateValueAndValidity();
      if (this._cardForm.invalid) {
        invalidInfo = true;
      }

      const numberTypes: any[] = this._cardTypeFn(this._value.cardNumber);
      if (numberTypes && numberTypes.length) {
        const type = numberTypes[0];
        if (type.lengths && !type.lengths.some(l => l === this._value.cardNumber.replace(/[\D]/g, '').length)) {
          this._invalidCardNumber = true;
        }
      }
    }

    if (this._invalidCardNumber) {
      return { 'invalid.card.number': true };
    } else if (invalidInfo) {
      return { 'invalid.card.info': true };
    }

    return null;
  }

  _checkDigit(event: KeyboardEvent) {
    if (!/\d/.test(event.key)) {
      event.preventDefault();
      event.stopPropagation();
    }
  }

  _checkMonth(month: string) {
    month = `${month || ''}`.replace(/[\D]/g, '').substring(0, 2);
    if (month) {
      const m = Number(month);
      if (m > 12) {
        this._value.expiryMonth = '12';
      } else if (m > 0) {
        this._value.expiryMonth = `${m}`;
      } else {
        this._value.expiryMonth = '';
      }
      this._shadowExpiryMonthInput.nativeElement.value = this._value.expiryMonth;
      this._shadowExpiryMonthInput.nativeElement.dispatchEvent(new Event('keyup'));
    } else {
      this._value.expiryMonth = '';
    }
    this.validate();
  }

  async _fieldsChanged() {
    this._changeFn(this._value);
    this._validatorChangeFn();
  }

  registerOnValidatorChange?(fn: () => void): void {
    this._validatorChangeFn = fn;
  }

  registerOnChange(fn: any): void {
    this._changeFn = fn;
  }

  registerOnTouched(fn: any): void {
    this._touchFn = fn;
  }

}

export interface CreditCardInfo {
  cardNumber?: string;
  holderFirstName?: string;
  holderLastName?: string;
  expiryMonth?: string;
  expiryYear?: string;
  cvv?: number;
  isValid?: boolean;
}
