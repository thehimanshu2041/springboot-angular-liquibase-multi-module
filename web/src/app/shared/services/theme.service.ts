import { Injectable } from '@angular/core';
import * as _ from 'lodash';
import * as tinycolor from 'tinycolor2';
import { LocalStorage } from 'ngx-webstorage';
import { Color, ColorScheme } from '../models/shared.model';

@Injectable()
export class ThemeService {

  @LocalStorage('primary') public primary: string;
  @LocalStorage('secondary') public secondary: string;
  @LocalStorage('accent') public accent: string;
  @LocalStorage('warn') public warn: string;
  @LocalStorage('isNightMode') public isNightMode: string;
  colorScheme = new ColorScheme();

  _isNightMode(): boolean {
    if (this.isNightMode && this.isNightMode === 'Y') {
      return true;
    } else {
      return false;
    }
  }

  _setColorOnOnload() {
    this.colorScheme = new ColorScheme();
    this.colorScheme.primaryColor = this.primary ? this.primary : '#008080';
    this.colorScheme.secondaryColor = this.secondary ? this.secondary : '#008080';
    this.colorScheme.accentColor = this.accent ? this.accent : '#d76b00';
    this.colorScheme.warnColor = this.warn ? this.warn : '#ff0000';
    this.colorScheme.isNightMode = this.isNightMode ? this.isNightMode : 'N';

    this.setPrimaryColor(this.colorScheme);
    this.setSecondaryColor(this.colorScheme);
    this.setAccentColor(this.colorScheme);
    this.setWarnColor(this.colorScheme);
    this._checkDarkMode(this.colorScheme);
  }

  _checkDarkMode(colorScheme: ColorScheme) {
    if (colorScheme.isNightMode === 'Y') {
      document.body.classList.remove('ts-theme-light');
      this.feedingColors(colorScheme);
      document.body.classList.add('ts-theme-dark');
    } else {
      document.body.classList.remove('ts-theme-dark');
      this.feedingColors(colorScheme);
      document.body.classList.add('ts-theme-light');
    }
  }

  setPrimaryColor(colorScheme: ColorScheme) {
    this.feedingColors(colorScheme);
    return this.savePrimaryPalletColor(colorScheme.primaryColor);
  }

  setSecondaryColor(colorScheme: ColorScheme) {
    this.feedingColors(colorScheme);
    return this.saveSecondaryPalletColor(colorScheme.secondaryColor);
  }

  setAccentColor(colorScheme: ColorScheme) {
    this.feedingColors(colorScheme);
    return this.saveAccentPalletColor(colorScheme.accentColor);
  }

  setWarnColor(colorScheme: ColorScheme) {
    this.feedingColors(colorScheme);
    return this.saveWarnPalletColor(colorScheme.warnColor);
  }

  feedingColors(colorScheme: ColorScheme) {
    this.savePrimaryPalletColor(colorScheme.primaryColor);
    this.saveSecondaryPalletColor(colorScheme.secondaryColor);
    this.saveAccentPalletColor(colorScheme.accentColor);
    this.saveWarnPalletColor(colorScheme.warnColor);
  }

  savePrimaryPalletColor(primary: string) {
    const primaryColorPalette = computeColors(primary);
    this.createPrimaryColorPalette(primaryColorPalette);
    return primaryColorPalette;
  }

  saveSecondaryPalletColor(secondary: string) {
    const secondaryColorPalette = computeColors(secondary);
    this.createSecondaryColorPalette(secondaryColorPalette);
    return secondaryColorPalette;
  }

  saveAccentPalletColor(accent: string) {
    const accentColorPalette = computeColors(accent);
    this.createAccentColorPalette(accentColorPalette);
    return accentColorPalette;
  }

  saveWarnPalletColor(warn: string) {
    const warnColorPalette = computeColors(warn);
    this.createWarnColorPalette(warnColorPalette);
    return warnColorPalette;
  }

  createPrimaryColorPalette(colorPalette: Color[]) {
    for (const color of colorPalette) {
      const key1 = `--theme-primary-${color.name}`;
      const value1 = color.hex;
      const key2 = `--theme-primary-contrast-${color.name}`;
      const value2 = color.darkContrast ? 'rgba(black, 0.87)' : 'white';
      document.documentElement.style.setProperty(key1, value1);
      document.documentElement.style.setProperty(key2, value2);
    }
  }

  createSecondaryColorPalette(colorPalette: Color[]) {
    for (const color of colorPalette) {
      const key1 = `--theme-secondary-${color.name}`;
      const value1 = color.hex;
      const key2 = `--theme-secondary-contrast-${color.name}`;
      const value2 = color.darkContrast ? 'rgba(black, 0.87)' : 'white';
      document.documentElement.style.setProperty(key1, value1);
      document.documentElement.style.setProperty(key2, value2);
    }
  }

  createAccentColorPalette(colorPalette: Color[]) {
    for (const color of colorPalette) {
      const key1 = `--theme-accent-${color.name}`;
      const value1 = color.hex;
      const key2 = `--theme-accent-contrast-${color.name}`;
      const value2 = color.darkContrast ? 'rgba(black, 0.87)' : 'white';
      document.documentElement.style.setProperty(key1, value1);
      document.documentElement.style.setProperty(key2, value2);
    }
  }

  createWarnColorPalette(colorPalette: Color[]) {
    for (const color of colorPalette) {
      const key1 = `--theme-warn-${color.name}`;
      const value1 = color.hex;
      const key2 = `--theme-warn-contrast-${color.name}`;
      const value2 = color.darkContrast ? 'rgba(black, 0.87)' : 'white';
      document.documentElement.style.setProperty(key1, value1);
      document.documentElement.style.setProperty(key2, value2);
    }
  }
}



function computeColors(hex: string): Color[] {
  return [
    getColorObject(tinycolor(hex).lighten(52), '50'),
    getColorObject(tinycolor(hex).lighten(37), '100'),
    getColorObject(tinycolor(hex).lighten(26), '200'),
    getColorObject(tinycolor(hex).lighten(12), '300'),
    getColorObject(tinycolor(hex).lighten(6), '400'),
    getColorObject(tinycolor(hex), '500'),
    getColorObject(tinycolor(hex).darken(6), '600'),
    getColorObject(tinycolor(hex).darken(12), '700'),
    getColorObject(tinycolor(hex).darken(18), '800'),
    getColorObject(tinycolor(hex).darken(24), '900'),
    getColorObject(tinycolor(hex).lighten(50).saturate(30), 'A100'),
    getColorObject(tinycolor(hex).lighten(30).saturate(30), 'A200'),
    getColorObject(tinycolor(hex).lighten(10).saturate(15), 'A400'),
    getColorObject(tinycolor(hex).lighten(5).saturate(5), 'A700')
  ];
}

function getColorObject(value: any, name: any): Color {
  const c = tinycolor(value);
  return {
    name,
    hex: c.toHexString(),
    darkContrast: c.isLight()
  };
}
