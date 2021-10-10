import { CurrencyPipe, formatCurrency } from '@angular/common';
import { DEFAULT_CURRENCY_CODE, Inject, LOCALE_ID, Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'tsUserCurrency'
})
export class UserCurrencyPipe implements PipeTransform {
  private ngCurrencyPipe: CurrencyPipe;
  constructor(@Inject(LOCALE_ID) private _localeId: string, @Inject(DEFAULT_CURRENCY_CODE) private _currencyCode: string) {
    this.ngCurrencyPipe = new CurrencyPipe(_localeId);
  }

  transform(value: number, digitsInfo?: string, styledHTML?: boolean): string {
    let str = this.ngCurrencyPipe.transform(value, this._currencyCode, 'symbol-narrow', digitsInfo, this._localeId);
    if (styledHTML && str) {
      const intPart = Math.floor(value);
      const fracPart = value - intPart;
      const intDigitsInfo = digitsInfo ? digitsInfo.replace(/^([0-9]+)\.[0-9]+-[0-9]+$/, '$1.0-0') : '1.0-0';
      const fracDigitsInfo = digitsInfo ? digitsInfo.replace(/^[0-9]+\.([0-9]+-[0-9]+)$/, '0.$1') : null;
      const intPartStr = formatCurrency(intPart, this._localeId, '', this._currencyCode, intDigitsInfo).trim();
      let fracPartStr = formatCurrency(fracPart, this._localeId, '', this._currencyCode, fracDigitsInfo)
        .trim()
        .replace(/^0[^0-9]/, '');
      if (fracPartStr === '0') {
        fracPartStr = '';
      }

      str = str
        .replace(
          new RegExp(`^([^0-9]*?)${intPartStr}(\\.|,){0,1}${fracPartStr}([^0-9]*?)$`),
          `<span class="currency-symbol">$1</span>` +
          `<span class="currency-integer">${intPartStr}</span>` +
          `<span class="currency-dec-point">$2</span>` +
          `<span class="currency-fraction">${fracPartStr}</span>` +
          `<span class="currency-symbol">$3</span>`
        )
        .replace(`<span class="currency-symbol"></span>`, '');
    }
    return str;
  }
}
