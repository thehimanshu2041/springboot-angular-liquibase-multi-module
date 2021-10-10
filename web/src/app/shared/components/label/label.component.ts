import { Component, ContentChild, HostBinding } from '@angular/core';
import { MatFormField } from '@angular/material/form-field';

/**
 * @internal
 */
@Component({
  selector: 'ts-label',
  templateUrl: './label.component.html',
  styleUrls: ['./label.component.scss']
})
export class LabelComponent {
  @ContentChild(MatFormField) _field: MatFormField;

  get _controlId(): string {
    return this._field?._control?.id;
  }

  @HostBinding('class.ts-focused')
  get _focusedClass(): boolean {
    return this._field?._control?.focused;
  }

  @HostBinding('class.ts-error-state')
  get _invalidClass(): boolean {
    return this._field?._control?.errorState;
  }

  @HostBinding('class.ts-primary')
  get _primaryClass(): boolean {
    return this._field?.color === 'primary';
  }

  @HostBinding('class.ts-accent')
  get _accentClass(): boolean {
    return this._field?.color === 'accent';
  }

  @HostBinding('class.ts-warn')
  get _warnClass(): boolean {
    return this._field?.color === 'warn';
  }
}
