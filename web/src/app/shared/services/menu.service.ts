import { Injectable } from '@angular/core';
import { Router, Event, NavigationEnd } from '@angular/router';
import { BehaviorSubject, Subject } from 'rxjs';
import { MenuDetail, MenuServiceService } from 'src/app/generated/rest';


@Injectable()
export class MenuService {

  menuSubject = new Subject<MenuDetail[]>();
  menuObservable$ = this.menuSubject.asObservable();
  public appDrawer: any;
  public currentUrl = new BehaviorSubject<string>(undefined);

  constructor(
    private router: Router,
    private trisulService: MenuServiceService) {
    this.router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        this.currentUrl.next(event.urlAfterRedirects);
      }
    });
  }

  getMenuList() {
    return this.trisulService.getMenuList().subscribe(res => this.menuSubject.next(res));
  }

  closeNav() {
    this.appDrawer.close();
  }

  openNav() {
    this.appDrawer.open();
  }
}
