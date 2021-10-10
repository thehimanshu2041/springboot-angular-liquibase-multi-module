import {
  HttpErrorResponse,
  HttpHandler,
  HttpHeaderResponse,
  HttpInterceptor,
  HttpProgressEvent,
  HttpRequest,
  HttpResponse,
  HttpSentEvent,
  HttpUserEvent
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as _ from 'lodash';
import { Observable } from 'rxjs';
import { tap, } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from './../services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {
  constructor(
    private router: Router,
    private authService: AuthService,
    private toastr: ToastrService
  ) { }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpSentEvent | HttpHeaderResponse | HttpProgressEvent | HttpResponse<any> | HttpUserEvent<any>> {
    return next.handle(req).pipe(tap(response => null, errorResponse => this.handleErrorResponse(errorResponse)));
  }

  handleErrorResponse(
    resp: HttpSentEvent | HttpHeaderResponse | HttpProgressEvent | HttpResponse<any> | HttpUserEvent<any>
  ): void {
    if (resp instanceof HttpErrorResponse) {
      let errAlert: string | ErrorObject = this.parseError(resp);
      if (errAlert) {
        if (_.isString(errAlert)) {
          this.toastr.error(errAlert);
        } else {
          this.toastr.error(errAlert.message);
        }
      }
    }
  }

  private parseError(errResp: HttpErrorResponse): string | ErrorObject {
    let errorObj: string | ErrorObject;
    if (errResp.error instanceof ErrorEvent) {
      errorObj = 'Something went wrong !!!';
    } else {
      if (errResp.status === 401) {
        errorObj = this.setError(errResp);
        this.authService.authSession = null;
        this.router.navigate(['login']);
      } else if (errResp.status === 403) {
        errorObj = this.setError(errResp);
      } else {
        errorObj = this.setError(errResp);
      }
    }
    return errorObj;
  }

  private setError(errResp: HttpErrorResponse): ErrorObject {
    let errorObj: ErrorObject = new ErrorObject();
    errorObj.message = errResp.error?.error?.message ? errResp.error.error.message : 'Something went wrong !!!';
    errorObj.description = errResp.error?.error?.description;
    return errorObj;
  }
}

export class ErrorObject {
  description: string;
  message: string;
}
