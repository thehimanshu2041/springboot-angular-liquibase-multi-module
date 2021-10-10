import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Optional, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { MatTabGroup } from '@angular/material/tabs';
import { CreditCardInfo } from './card-detail.component';
import { CardDetailComponent } from './card-detail.component';

@Component({
  selector: 'credit-card-component',
  template: '<adv-credit-card [(ngModel)]="_cardInfo" #ccModel="ngModel"  (ngModelChange)="_onAddCreditCard(ccModel.valid);" [hideRequiredMarker]="true"></adv-credit-card>',
})
export class CreditCardComponent implements OnInit {

  _cardAdded = false;
  _form: FormGroup;
  _cardInfo: CreditCardInfo;

  @Output() paymentType = new EventEmitter();
  @Output() cardInfo = new EventEmitter();
  @ViewChild(CardDetailComponent, { static: true }) creditCardComponent: CardDetailComponent;
  @ViewChild(MatTabGroup, { static: true }) tabGroup: MatTabGroup;
  @ViewChild('creditCardTmpl', { static: true }) _cardDlgTmpl: TemplateRef<any>;

  constructor(
    @Optional() private _pFGD: FormGroupDirective,
    @Optional() private _pNGF: NgForm) { }

  ngOnInit() {
    if (!this._pFGD && !this._pNGF) {
      throw new Error('WebAppInvPaymentComponent should be used in an Angular Form');
    }
    this._form = (this._pFGD || this._pNGF).form;
    const fb = new FormBuilder();
    this._form.addControl('isValid', fb.control(null, Validators.requiredTrue));
    this._form.addControl('creditCardDetail', fb.control(null));
  }

  _onAddCreditCard(valid: boolean) {
    this._cardAdded = valid;
    this._form.patchValue({
      creditCardDetail: this._cardInfo,
      isValid: valid
    });
    this._form.updateValueAndValidity();
  }

}
