import { Injector, Pipe, PipeTransform } from '@angular/core';


declare const intlTelInputUtils: any;

@Pipe({
  name: 'tsTel',
  pure: true
})
export class PhoneNumberPipe implements PipeTransform {
  constructor(private _injector: Injector) { }

  transform(value: any, mode: string, renderLink: boolean): string {
    if (!value) {
      return value;
    }
    let countryOfOpCode = 'US';
    let format: number;
    switch (mode) {
      case 'NATIONAL':
        format = intlTelInputUtils.numberFormat.NATIONAL;
        break;
      default:
        format = intlTelInputUtils.numberFormat.INTERNATIONAL;
    }
    try {
      const ph = value || '';
      const result = intlTelInputUtils.formatNumber(value || '', countryOfOpCode, format);
      if (renderLink) {
        return `<a href="tel:${result}">${result}</a>`;
      } else {
        return result;
      }
    } catch (e) {
      console.warn(`${e.message}: ${value}`);
      return value;
    }
  }
}
