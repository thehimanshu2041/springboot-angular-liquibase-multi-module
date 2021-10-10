import { Component, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { AuthServiceService, CodeDetail, StaticDataServiceService, UserDetail } from 'src/app/generated/rest';
import { predefinedAnimations } from 'src/app/shared';
import { validatePasswordStrength } from 'src/app/shared/directives/password-strength-validator.directive';

@Component({
  selector: 'ts-registration-component',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss'],
  animations: [predefinedAnimations.zoomInOutAnimations]
})
export class RegistrationComponent {
  registrationFormGroup: FormGroup = new FormGroup({
    personalFromGroup: new FormGroup({}),
    contactFromGroup: new FormGroup({}),
    credentialFromGroup: new FormGroup({}),
    addressFromGroup: new FormGroup({}),
    cardFromGroup: new FormGroup({}),
    declarationFromGroup: new FormGroup({})
  });
  availableGender$: Observable<CodeDetail[]>;
  availableTitle$: Observable<CodeDetail[]>;
  _passwordRevealed: boolean;
  _submitted: boolean;
  _submitting: boolean;

  @ViewChild('stepper', { static: false })
  private _stepper: MatStepper;

  constructor(
    private staticDataService: StaticDataServiceService,
    private authService: AuthServiceService) { }

  ngOnInit() {
    this._initFormControls();
    this._initObsServices();
  }

  submit(): void {
    if (this.registrationFormGroup.valid) {
      const { title, firstName, gender, dob, lastName } = this.registrationFormGroup.controls.personalFromGroup.value;
      const { email, mobile } = this.registrationFormGroup.controls.contactFromGroup.value;
      const { username, password } = this.registrationFormGroup.controls.credentialFromGroup.value;
      const addressDetail = this.registrationFormGroup.controls.addressFromGroup.value;
      const cardDetail = this.registrationFormGroup.controls.cardFromGroup.value.creditCardDetail;
      const formattedMobile = mobile.replace(/\s/g, '');
      const userDetail: UserDetail = {
        title,
        firstName,
        gender,
        dob,
        lastName,
        email,
        mobile: formattedMobile.substring(formattedMobile.length - 10),
        username,
        password,
        cardDetail: {
          cardNumber: cardDetail.cardNumber.replace(/\s/g, ''),
          cardFirstName: cardDetail.holderFirstName,
          cardLastName: cardDetail.holderLastName,
          cardExpireMonth: cardDetail.expiryMonth,
          cardExpireYear: cardDetail.expiryYear,
          cardCvv: cardDetail.cvv
        },
        addressDetail: {
          addressAddress1: addressDetail.addressAddress1,
          addressAddress2: addressDetail.addressAddress2,
          addressAddress3: addressDetail.addressAddress3,
          addressCountry: addressDetail.addressCountry,
          addressState: addressDetail.addressState,
          addressCity: addressDetail.addressCity
        }
      };
      this.registrationFormGroup.disable();
      this._submitting = true;
      this.authService.doRegistration(userDetail).subscribe({
        next: () => {
          this._submitted = true;
          this._submitting = false;
          this._stepper.steps.forEach((step, _i) => {
            step.state = 'done';
            step.completed = true;
            step.hasError = false;
            step.editable = false;
            this._stepper.next();
          });

        }, error: () => {
          this._submitting = false;
          this.registrationFormGroup.enable();
        }
      });
    }
  }

  private _initObsServices(): void {
    this.availableGender$ = this.staticDataService.getCodeType('GENDER');
    this.availableTitle$ = this.staticDataService.getCodeType('TITLE');
  }

  private _initFormControls(): void {
    const fb = new FormBuilder();
    this._addControl('title', fb.control(null, Validators.required), 'personalFromGroup');
    this._addControl('firstName', fb.control(null, Validators.required), 'personalFromGroup');
    this._addControl('gender', fb.control(null, Validators.required), 'personalFromGroup');
    this._addControl('dob', fb.control(moment().subtract(18, 'years'), Validators.required), 'personalFromGroup');
    this._addControl('lastName', fb.control(null, Validators.required), 'personalFromGroup');
    this._addControl('email', fb.control(null, [Validators.required, Validators.email]), 'contactFromGroup');
    this._addControl('mobile', fb.control(null, Validators.required), 'contactFromGroup');
    this._addControl('username', fb.control(null, Validators.required), 'credentialFromGroup');
    this._addControl('password', fb.control(null, Validators.required, validatePasswordStrength), 'credentialFromGroup');
    this._addControl('declaration', fb.control(null, Validators.requiredTrue), 'declarationFromGroup');
  }

  private _addControl(name: string, control: AbstractControl, formGroupName: string) {
    if (!(this.registrationFormGroup.get(formGroupName) as FormGroup).contains(name)) {
      (this.registrationFormGroup.get(formGroupName) as FormGroup).addControl(name, control);
    }
  }

  /* private _removeControl(name: string, formGroup: FormGroup) {
    if (formGroup.contains(name)) {
      formGroup.removeControl(name);
    }
  } */

}
