import { Component, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { ReplaySubject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { predefinedAnimations } from 'src/app/shared';
import { AuthService } from 'src/app/shared/services/auth.service';

@Component({
  selector: 'ts-login-component',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  animations: [predefinedAnimations.zoomInOutAnimations]
})
export class LoginComponent implements OnDestroy {

  username: string;
  password: string;

  @ViewChild('usernameInput', { static: false }) usernameInput: ElementRef;
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);

  constructor(private authService: AuthService, private router: Router) { }

  login(): void {
    this.authService.login(this.username, this.password)
      .pipe(takeUntil(this.destroyed$))
      .subscribe(data => {
        if (data) {
          this.router.navigateByUrl('dashboard');
        }
      })
  }

  ngOnDestroy(): void {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }

}
