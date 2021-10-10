import { CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import {
  HorizontalMainMenuComponent,
  HorizontalMenuComponent,
  NavBarComponent,
  VerticalMenuComponent
} from './container';
import {
  SharedModule
} from './shared';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AddressComponent, CardDetailComponent, CreditCardComponent, DashboardComponent, DemoTileComponent, LoginComponent, PasswordStrengthMeterComponent, RegistrationComponent, UserInfoTileComponent } from './main';
import { AuthGuard, ErrorHandlerInterceptor, RequestOptionInterceptor } from './shared/authentication';
import { NoAuthGuard } from './shared/authentication/no.auth.guard';
import { ToBeImplementedComponent } from './main/dashboard/custom-tile-page';
import { CustomTilePageComponent } from './main/dashboard/custom-tile-page/custom.tile.page.component';

const DECLARE_COMPONENT = [
  AppComponent,
  NavBarComponent,
  HorizontalMainMenuComponent,
  HorizontalMenuComponent,
  VerticalMenuComponent,
  LoginComponent,
  RegistrationComponent,
  PasswordStrengthMeterComponent,
  DashboardComponent,
  CustomTilePageComponent,
  ToBeImplementedComponent,
  AddressComponent,
  CardDetailComponent,
  CreditCardComponent
];

const TILE_TYPES = [DemoTileComponent, UserInfoTileComponent];

TILE_TYPES.forEach(f => f);

@NgModule({
  declarations: [
    DECLARE_COMPONENT,
    TILE_TYPES
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    SharedModule,
    AppRoutingModule
  ],
  providers: [
    AuthGuard,
    NoAuthGuard,
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: RequestOptionInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true,
    },
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA,
    NO_ERRORS_SCHEMA
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
  constructor() { }
}
