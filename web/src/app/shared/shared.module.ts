import { CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { MaterialModule } from './modules/material/material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LayoutModule } from '@angular/cdk/layout';
import { OverlayModule } from '@angular/cdk/overlay';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { AlertService, ThemeService } from './services';
import { StaticTranslateLoader } from './translate';
import { TranslateLoader, TranslateModule, TranslateStore } from '@ngx-translate/core';
import { AuthServiceService, MenuServiceService, StaticDataServiceService, UserServiceService } from 'src/app/generated/rest';
import { AuthService } from './services/auth.service';
import { ToastrModule } from 'ngx-toastr';
import { AlertAreaModule, LabelModule } from './components';
import { MenuService } from './services/menu.service';
import { TooltipModule } from './components/tooltip';
import { DirectiveModule } from './directives/directive.module';
import { PipeModule } from './pipes/pipe.module';
import { CardModule } from 'ngx-card';

const EXPORTED_SERVICES = [
  MenuServiceService,
  UserServiceService,
  AuthServiceService,
  StaticDataServiceService
];

const EXPORTED_MODULES = [
  AlertAreaModule,
  LabelModule,
  TooltipModule,
  DirectiveModule,
  PipeModule
];

const toastConfig = {
  positionClass: 'toast-top-right',
  closeButton: true,
  timeOut: 10000,
  easeTime: 300,
  enableHtml: true,
  progressBar: true,
  preventDuplicates: true
};

@NgModule({
  declarations: [
  ],
  imports: [
    MaterialModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    LayoutModule,
    OverlayModule,
    CardModule,
    NgxWebstorageModule.forRoot({ prefix: 'TRISUL|' }),
    TranslateModule.forChild({
      defaultLanguage: 'en',
      loader: {
        provide: TranslateLoader,
        useExisting: StaticTranslateLoader
      },
    }),
    ToastrModule.forRoot(toastConfig),
    ...EXPORTED_MODULES
  ],
  exports: [
    MaterialModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    LayoutModule,
    OverlayModule,
    NgxWebstorageModule,
    CardModule,
    TranslateModule,
    ToastrModule,
    ...EXPORTED_MODULES
  ],
  providers: [
    StaticTranslateLoader,
    TranslateStore,
    AlertService,
    ThemeService,
    MenuService,
    AuthService,
    ...EXPORTED_SERVICES
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA,
    NO_ERRORS_SCHEMA
  ]
})
export class SharedModule { }
