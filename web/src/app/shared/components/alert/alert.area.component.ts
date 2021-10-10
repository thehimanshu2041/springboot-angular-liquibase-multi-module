import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, Input, OnDestroy, TemplateRef, ViewChild, ViewContainerRef } from '@angular/core';
import { MediaObserver } from '@angular/flex-layout';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NavigationEnd, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import * as _ from 'lodash';
import { Subscription, timer } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Alert, AlertService } from 'src/app/shared/services/alert.service';

let instances: AlertAreaComponent[] = [];

@Component({
  selector: 'ts-alert-area',
  templateUrl: './alert.area.component.html',
  animations: [
    trigger('alertShown', [
      state('visible', style({})),
      transition('void => *', [style({ transform: 'translateY(100%)', opacity: '0' }), animate(300)]),
      transition('* => void', [animate(300, style({ transform: 'translateY(-100%)', opacity: '0' }))])
    ])
  ]
})
export class AlertAreaComponent implements OnDestroy {
  alertsOnDisplay: any[] = [];
  instanceId: number;
  @Input() max: number = 3;
  @Input() retreatToSnackbar: boolean = false;

  @ViewChild('snackbarTemplate', { read: TemplateRef, static: true })
  private _snackbarTmpl: TemplateRef<any>;

  private _alertSubscription: Subscription;
  private _routerSubscription: Subscription;

  constructor(
    private alertService: AlertService,
    router: Router,
    private _snackbar: MatSnackBar,
    private _media: MediaObserver,
    private _translate: TranslateService,
    private _vc: ViewContainerRef
  ) {
    this.instanceId = instances.length;
    instances.push(this);
    this._alertSubscription = this.alertService.alertEvent
      .pipe(delay(10))
      .subscribe((alert: Alert) => this._processAlert(alert));

    this._routerSubscription = router.events.subscribe(ev => {
      if (ev instanceof NavigationEnd) {
        this.alertsOnDisplay = [];
        this._snackbar.dismiss();
      }
    });
  }

  ngOnDestroy(): void {
    this._alertSubscription.unsubscribe();
    this._routerSubscription.unsubscribe();
    instances.pop();
  }

  closeAlert(i: number): void {
    _.remove(this.alertsOnDisplay, (val, index) => index === i);
  }

  dismissSnackbar() {
    this._snackbar.dismiss();
  }

  private async _processAlert(alert: Alert) {
    if (!alert) {
      this.alertsOnDisplay = [];
      return;
    }

    if (this.instanceId === instances.length - 1) {
      if (this._media.isActive('gt-xs') || !this.retreatToSnackbar) {
        if (this.alertsOnDisplay.length >= this.max) {
          this.alertsOnDisplay = _.drop(this.alertsOnDisplay);
        }
        await timer(500).toPromise();
        this.alertsOnDisplay.push(alert);
        let elem = document.querySelector(`#alert_area_${this.instanceId}`);
        if (elem) {
          elem.scrollIntoView({ behavior: 'smooth', block: 'end' });
        }
      } else {
        this.alertsOnDisplay = [];
        let warning = alert.severity === 'error' || alert.severity === 'warn';
        const bgClasses = {
          error: 'bg-danger',
          warn: 'bg-danger',
          info: 'bg-info',
          success: 'bg-success'
        };
        this._translate.get(alert.message, alert.argv).subscribe(text =>
          this._snackbar.openFromTemplate(this._snackbarTmpl, {
            panelClass: bgClasses[alert.severity] || [],
            duration: warning ? undefined : 5000,
            verticalPosition: 'top',
            viewContainerRef: this._vc,
            data: {
              message: text,
              autoclose: warning ? false : true
            }
          })
        );
      }
    }
  }
}
