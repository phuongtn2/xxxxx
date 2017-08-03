import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { ForgotPasswordService } from './forgot-password.service';
import { ServerError } from '../core/objects/server-error';
import { ErrorCode } from '../core/enum/error-code.enum';
import { NotificationService } from '../core/services/notification.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
  providers: [ForgotPasswordService]
})
export class ForgotPasswordComponent implements OnInit {
  private title: string;
  email: string;
  getCodeSubmitted = false;
  confirmCode: string;
  newPassword: string;
  confirmPassword: string;

  constructor(
    private titleService: Title,
    private router: Router,
    private route: ActivatedRoute,
    private notification: NotificationService,
    private forgotPasswordService: ForgotPasswordService
  ) {
    this.title = 'Quên mật khẩu';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);

    this.getCodeSubmitted = this.route.snapshot.queryParams.hasOwnProperty('code');
    if (this.getCodeSubmitted) {
      this.confirmCode = this.route.snapshot.queryParams['code'];
    }
  }

  getCode() {
    this.forgotPasswordService.getCode(this.email)
      .catch(
        (response: Response) => {
          let msg = 'Unknown error. Please contact administrator for further support.';
          if (response.status === 422) {
            const err: ServerError = response.json();
            if (err.error &&
              (
                err.error.toString() === ErrorCode.EmailIncorrect.toString() ||
                err.error.toString() === ErrorCode.AccountNotFound.toString()
              )
            ) {
              msg = err.error_description;
            }
          }
          this.notification.notify(msg);

          return Observable.throw(response);
        }
      )
      .subscribe(
        () => {
          this.getCodeSubmitted = true;
          this.notification.notify(`Confirm code has been send to ${this.email}`);
        }
      );
  }

  resetPassword() {
    this.forgotPasswordService.resetPassword(this.confirmCode, this.newPassword, this.confirmPassword)
      .catch(
        (response: Response) => {
          let msg = 'Unknown error. Please contact administrator for further support.';
          if (response.status === 409) {
            const err: ServerError = response.json();
            if (err.error && err.error.toString() === ErrorCode.ClientIDError.toString()) {
              msg = err.error_description;
            }
          }
          if (response.status === 422) {
            const err: ServerError = response.json();
            if (err.error && err.error.toString() === ErrorCode.CodeNotFound.toString()) {
              msg = err.error_description;
            }
          }
          this.notification.notify(msg);
          this.router.navigate(['/login']);

          return Observable.throw(response);
        }
      )
      .subscribe(
        () => {
          this.notification.notify('Your password has been reset.');
          this.router.navigate(['/login']);
        }
      );
  }
}
