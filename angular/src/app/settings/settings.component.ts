import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Response } from '@angular/http';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { User } from '../core/models/user';
import { NotificationService } from '../core/services/notification.service';
import { SettingService } from './setting.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
  providers: [SettingService]
})
export class SettingsComponent implements OnInit {
  title: string;
  user: User;
  settingsForm: FormGroup;

  private validationMessages = {
    firstName: {
      required: 'Cần nhập Họ.'
    },
    lastName: {
      required: 'Cần nhập Tên.'
    },
    currentPassword: {
      requiredIf: 'Chưa nhập Mật khẩu mới.'
    },
    newPassword: {
      requiredIf: 'Chưa nhập mật khẩu cũ.'
    },
    confirmPassword: {
      isEquals: 'Mật khẩu xác nhận không giống.'
    }
  };

  formErrors = {
    firstName: '',
    lastName: '',
    currentPassword: '',
    newPassword: '',
    confirmPassword: ''
  };

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private settingService: SettingService,
    private notification: NotificationService,
    private router: Router
  ) {
    this.title = 'Thiết Lập';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.buildForm();
    this.getUserInfo();
    this.user = this.user || new User();
  }

  getUserInfo() {
    this.settingService.getUserInfo().subscribe(
      user => {
        this.user = user;
        this.setFormData();
      }
    );
  }

  changeUserInfo() {
    if (this.settingsForm.get('firstName').value !== this.user.firstName ||
      this.settingsForm.get('lastName').value !== this.user.lastName
    ) {
      this.changeName();
    }

    if (this.settingsForm.get('newPassword').value) {
      this.changePassword();
    }
  }

  changeName() {
    if (this.settingsForm.get('firstName').value !== this.user.firstName ||
      this.settingsForm.get('lastName').value !== this.user.lastName
    ) {
      const formModel = this.settingsForm.value;
      this.settingService.changeName(formModel.firstName, formModel.lastName)
        .catch(
          (response: Response) => {
            this.notification.notify('Có lỗi xảy ra.');
            return Observable.throw(response);
          }
        )
        .subscribe(
          data => {
            this.user = data;
            this.notification.notify('Đổi tên thành công.');
          }
        );
    } else {
      this.notification.notify('Có lỗi xảy ra.');
    }
  }

  changePassword() {
    const formModel = this.settingsForm.value;
    this.settingService.changePassword(formModel.currentPassword, formModel.newPassword, formModel.confirmPassword)
      .catch(
        (response: Response) => {
          this.notification.notify('Có lỗi xảy ra.');
          return Observable.throw(response);
        }
      )
      .subscribe(
        data => {
          this.user = data;
          this.notification.notify('Đổi password thành công.');
          this.router.navigate(['./login']);
        }
      );
  }

  private buildForm() {
    this.settingsForm = this.fb.group({
      lastName: [''],
      firstName: [''],
      currentPassword: [''],
      newPassword: [''],
      confirmPassword: ['']
    });
    // this.settingsForm.get('currentPassword').setValidators(this.requiredIf('newPassword'));
    // this.settingsForm.get('newPassword').setValidators(this.requiredIf('currentPassword'));
    // this.settingsForm.get('confirmPassword').setValidators(this.isEquals('newPassword'));

    this.settingsForm.valueChanges.subscribe(() => this.onValueChanges());
  }

  private onValueChanges() {
    if (!this.settingsForm) {
      return;
    }
    const form = this.settingsForm;

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

  private setFormData() {
    this.settingsForm.patchValue({
      firstName: this.user.firstName,
      lastName: this.user.lastName
    });
  }

  private requiredIf(compareControl: string) {
    const settingsForm = this.settingsForm;
    return (control: AbstractControl): ValidationErrors | null => {
      console.log(this.settingsForm);
      if (!settingsForm.get(compareControl) || !control.value) {
        return null;
      }
      return control.value && settingsForm.get(compareControl).value ? null : {requiredIf: true};
    };
  }

  private isEquals(compareControl: string) {
    const settingsForm = this.settingsForm;
    return (control: AbstractControl): ValidationErrors | null => {
      console.log(this.settingsForm);
      if (!(settingsForm.get(compareControl) && settingsForm.get(compareControl).value)) {
        return null;
      }
      const collator = new Intl.Collator();
      return collator.compare(control.value, settingsForm.get(compareControl).value) === 0 ? null : {isEquals: true};
    };
  }
}
