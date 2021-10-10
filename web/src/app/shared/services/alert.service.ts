import { EventEmitter, Injectable } from '@angular/core';
import * as _ from 'lodash';

export type Severity = 'success' | 'info' | 'warn' | 'error';

export type AlertElement = {
    message: string;
    argv: any;
};

export type AlertBody = { [key: string]: string | AlertElement };

export class Alert {
    argv: any;
    longWords: boolean;

    constructor(
        public severity: Severity,
        public message: string,
        alertObject: AlertElement,
        public key?: string
    ) {
        if (alertObject) {
            this.message = alertObject.message;
            this.argv = alertObject.argv;
        }
        if (this.message) {
            this.longWords = this.message
                .split(/\s/)
                .map(s => s.length)
                .some(l => l > 80);
        }
    }

    getType = () => (this.severity === 'error' ? 'danger' : this.severity === 'warn' ? 'warning' : this.severity);
}

@Injectable()
export class AlertService {
    readonly alertEvent: EventEmitter<Alert> = new EventEmitter<Alert>();

    raise(alertBody: AlertBody | string | (AlertBody | string)[]): void {
        let alerts: (AlertBody | string)[] = _.isArray(alertBody)
            ? (alertBody as (AlertBody | string)[])
            : [alertBody as AlertBody | string];
        alerts.forEach(a => {
            let alert: Alert = this.toAlert(a);
            if (alert.severity && alert.message) {
                this.alertEvent.emit(alert);
            }
        });
    }

    clear() {
        this.alertEvent.emit(null);
    }

    toAlert(alertBody: AlertBody | string): Alert {
        alertBody = alertBody || '';
        let sevr = _.first(_.keys(alertBody)) as Severity;
        let msgKey: any = _.get(alertBody, sevr);
        if (_.isString(msgKey)) {
            return new Alert(sevr, msgKey, undefined);
        } else {
            return new Alert(sevr, undefined, msgKey);
        }
    }

    raiseAlert(alert: Alert) {
        this.alertEvent.emit(alert);
    }
}
