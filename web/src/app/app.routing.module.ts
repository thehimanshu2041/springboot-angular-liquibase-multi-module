import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent, LoginComponent } from './main';
import { RegistrationComponent } from './main/registration';
import { AuthGuard } from './shared/authentication';
import { NoAuthGuard } from './shared/authentication/no.auth.guard';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    canActivate: [NoAuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [NoAuthGuard]
  },
  {
    path: 'registration',
    component: RegistrationComponent,
    canActivate: [NoAuthGuard]
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
