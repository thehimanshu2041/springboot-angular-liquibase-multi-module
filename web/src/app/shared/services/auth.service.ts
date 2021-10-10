import { Injectable } from "@angular/core";
import { SessionStorage } from "ngx-webstorage";
import { Observable, of } from "rxjs";
import { mergeMap } from "rxjs/operators";
import { AuthenticationDetail, AuthServiceService } from "src/app/generated/rest";
import { MenuService } from ".";


@Injectable()
export class AuthService {

  @SessionStorage('AUTHDETAIL')
  public authSession: AuthenticationDetail;

  constructor(
    private authService: AuthServiceService,
    private menuService: MenuService) { }

  login(username: string, password: string): Observable<boolean> {
    const loginPayload = { username: username, password: password };
    return this.authService.doLogin(loginPayload)
      .pipe(
        mergeMap((map) => {
          if (map.access_token) {
            this.actionOnLogin(map);
            return of(true);
          } else {
            this.actionOnLogout();
            return of(false);
          }
        })
      );
  }

  actionOnLogin(authDetail: AuthenticationDetail): void {
    this.authSession = authDetail;
    this.menuService.getMenuList();
  }

  actionOnLogout(): void {
    this.authSession = null;
    this.menuService.getMenuList();
  }

  isAuthenticated(): boolean {
    if (this.authSession && this.authSession.access_token) {
      return true;
    }
    return false;
  }
}
