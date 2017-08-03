import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Response } from '@angular/http';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { AuthenticationService } from '../core/services/authentication.service';
import { NotificationService } from '../core/services/notification.service';
import { ServerError } from '../core/objects/server-error';
import { ErrorCode } from '../core/enum/error-code.enum';
import { SettingService } from '../settings/setting.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [SettingService]
})
export class LoginComponent implements OnInit {
  private title: string;
  loginForm: FormGroup;

  formErrors = {
    username: '',
    password: ''
  };

  private validationMessages = {
    username: {
      required: 'Cần nhập Tài Khoản.'
    },
    password: {
      required: 'Cần nhập Mật Khẩu.'
    }
  };

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private notification: NotificationService,
    private authService: AuthenticationService,
    private settingService: SettingService
  ) {
    this.title = 'Đăng Nhập';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.buildForm();
    this.authService.logout();
  }

  login() {
    const formModel = this.loginForm.value;
    this.authService.login(formModel.username, formModel.password)
      .catch(
        (response: Response) => {
          if (response.status === 400 || response.status === 401) {
            const err: ServerError = response.json();
            if (err.error &&
              (
                err.error.toString() === ErrorCode.Unauthorized.toString() ||
                err.error.toString() === ErrorCode.InvalidGrant.toString()
              )
            ) {
              this.notification.notify('Username or password is invalid');
            }
          }
          return Observable.throw(response);
        }
      )
      .subscribe(
        () => {
          const returnUrl = this.activatedRoute.snapshot.queryParams['back'] || '/';
          this.settingService.getUserInfo()
            .subscribe(() => {
              this.authService.rememberMe = formModel.rememberLogin;
              this.router.navigateByUrl(returnUrl);
            });
        }
      );
  }

  private buildForm() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      rememberLogin: [this.authService.rememberMe]
    });
    this.loginForm.valueChanges
      .subscribe(() => this.onValueChanges());
  }

  private onValueChanges() {
    if (!this.loginForm) {
      return;
    }
    const form = this.loginForm;

    for (const field of Object.keys(this.formErrors)) {
      // clear previous error message if any
      this.formErrors[field] = '';
      const control = form.get(field);

      if (control && control.invalid) {
        const messages = this.validationMessages[field];
        const keys = Object.keys(control.errors);
        if (!keys.length) {
          continue;
        }
        const key = keys.shift();
        this.formErrors[field] = messages[key];
      }
    }
  }
}
