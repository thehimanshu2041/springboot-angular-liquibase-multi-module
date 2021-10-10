import { Component } from '@angular/core';
import { MatIconRegistry } from '@angular/material/icon';
import { ThemeService } from './shared/services/theme.service';

@Component({
  selector: 'ts-app-root',
  template: '<ts-trisul-container-component></ts-trisul-container-component>'
})
export class AppComponent {

  constructor(themeService: ThemeService, registry: MatIconRegistry) {
    registry.setDefaultFontSetClass('material-icons');
    themeService._setColorOnOnload();
  }
}
