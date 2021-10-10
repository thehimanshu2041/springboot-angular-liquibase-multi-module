import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl, Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { CscDetail, StaticDataServiceService } from 'src/app/generated/rest';

@Component({
  selector: 'ts-address-component',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.scss']
})
export class AddressComponent implements OnInit {

  @Input() addressForm: FormGroup;
  availableCountry$: Observable<CscDetail[]>;
  availableState$: Observable<CscDetail[]>;
  availableCity$: Observable<CscDetail[]>;

  constructor(private staticDataService: StaticDataServiceService) { }


  ngOnInit(): void {
    this._initFormControls();
    this._initObsServices();
  }

  private _initObsServices(): void {
    this.availableCountry$ = this.staticDataService.getCountries();
    this.addressForm.controls.addressCountry.valueChanges.subscribe(() =>
      this.availableState$ = this.staticDataService.getStatesByCountryId(
        (this.addressForm.get('addressCountry').value as CscDetail).cscID
      ));
    this.addressForm.controls.addressState.valueChanges.subscribe(() =>
      this.availableCity$ = this.staticDataService.getCitiesByStateId(
        (this.addressForm.get('addressState').value as CscDetail).cscID
      ));
  }

  private _initFormControls(): void {
    const fb = new FormBuilder();
    this._addControl('search', fb.control(null));
    this._addControl('addressAddress1', fb.control(null, Validators.required));
    this._addControl('addressAddress2', fb.control(null));
    this._addControl('addressAddress3', fb.control(null));
    this._addControl('addressCity', fb.control(null, Validators.required));
    this._addControl('addressState', fb.control(null, Validators.required));
    this._addControl('addressCountry', fb.control(null, Validators.required));
  }

  private _addControl(name: string, control: AbstractControl) {
    if (!this.addressForm.contains(name)) {
      this.addressForm.addControl(name, control);
    }
  }

}
