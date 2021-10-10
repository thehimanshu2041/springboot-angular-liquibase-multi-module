import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ReplaySubject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { MenuDetail } from 'src/app/generated/rest';
import { MenuService } from 'src/app/shared';
import { AuthService } from 'src/app/shared/services/auth.service';

@Component({ template: '' })
export class BaseContainerComponent implements OnInit, OnDestroy {

  items: MenuDetail[];
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);

  constructor(
    public menuService: MenuService,
    public authService: AuthService,
    public router: Router
  ) {
    this.menuService.getMenuList();
  }

  ngOnInit() {
    this.menuService.menuObservable$
      .pipe(takeUntil(this.destroyed$))
      .subscribe((res) => this.items = res);
  }

  ngOnDestroy(): void {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }

  get isLoggedIn(): boolean {
    return this.authService.isAuthenticated();
  }

  logout(): void {
    this.authService.actionOnLogout();
    this.menuService.closeNav();
    this.router.navigateByUrl('login');
  }
}
