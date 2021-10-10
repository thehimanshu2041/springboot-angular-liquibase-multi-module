import { Route } from "@angular/compiler/src/core";
import { Component } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot } from "@angular/router";
import { Observable, of } from "rxjs";
import { map } from "rxjs/operators";
import { AuthenticationDetail } from "src/app/generated/rest";
import { AuthService } from "../services/auth.service";
import * as _ from 'lodash';

export interface PermissionBasedRoute extends Route {
  permissionsRequired?: string[];
  children?: PermissionBasedRoute[];
}


@Component({ template: '' })
export class AuthGuard implements CanActivate, CanActivateChild {

  private user: AuthenticationDetail;
  private init: Observable<boolean>;

  constructor(private router: Router, private authService: AuthService) { }

  getUser(): Observable<boolean> {
    if (this.authService.isAuthenticated()) {
      this.user = this.authService.authSession;
      return of(true);
    } else {
      this.user = null;
      this.router.navigate(['login']);
      return of(false);
    }
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    this.init = this.getUser();
    return this.checkForActivation(route, state);
  }

  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    this.init = this.getUser();
    return this.checkForActivation(route, state);
  }

  private checkForActivation(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.init.pipe(map(() => {
      if (!this.user) {
        return false;
      }
      let permitted: boolean = false;
      let cfg = route.routeConfig as PermissionBasedRoute;
      if (cfg.permissionsRequired && cfg.permissionsRequired.length) {
        permitted = _.every(cfg.permissionsRequired, p =>
          _.some(this.user.roles, r => this.compareRoles('ROLE_' + r, p)));
      } else {
        permitted = true;
      }
      if (!permitted) {
        this.router.navigate(['login']);
        return false;
      }
      return true;
    }));
  }

  private compareRoles(userRole: string, targetRole: string | RegExp): boolean {
    if (!targetRole) {
      return false;
    }

    let regexp = new RegExp(targetRole);
    return regexp.test(userRole);
  }
}
