import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Response } from '@angular/http';
import { MdDialogRef } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs/Observable';

import { FarmerRelative } from '../../core/models/farmerRelative';
import { NotificationService } from '../../core/services/notification.service';
import { FarmerRelativeService } from '../farmer-relative.service';

@Component({
  selector: 'app-farmer-relative-register-dialog',
  templateUrl: './farmer-relative-register-dialog.component.html',
  styleUrls: ['./farmer-relative-register-dialog.component.scss']
})
export class FarmerRelativeRegisterDialogComponent implements OnInit {
  farmerId: number;
  title: string;
  farmerInfo: FormGroup;
  relative: FarmerRelative;
  isEditing = false;
  address;

  formErrors = {
    firstName: '',
    lastName: '',
    identityCard: '',
    phone: '',
    relationship: ''
  };

  private validationMessages = {
    firstName: {
      required: 'Cần nhập Họ.'
    },
    lastName: {
      required: 'Cần nhập Tên.'
    },
    identityCard: {
      pattern: 'Số CMND không hợp lệ.'
    },
    phone: {
      pattern: 'Số điện thoại không hợp lệ.'
    },
    relationship: {
      required: 'Cần nhập Quan hệ.'
    }
  };

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private farmerRelativeService: FarmerRelativeService,
    private notification: NotificationService,
    private dialogRef: MdDialogRef<FarmerRelativeRegisterDialogComponent>,
  ) {
    this.title = 'Đăng Ký Nhân Thân';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.buildForm();

    if (this.relative) {
      this.setFormData();
    }
  }

  private setFormData() {
    this.farmerInfo.setValue({
      id: this.relative.id,
      firstName: this.relative.firstName,
      lastName: this.relative.lastName,
      identityCard: this.relative.identityCard,
      phone: this.relative.phone,
      email: this.relative.email,
      relationship: this.relative.relationship,
      address: {
        street: this.relative.address,
        provinceId: this.relative.provinceId,
        districtId: this.relative.districtId,
        wardId: this.relative.wardId
      }
    });
    // TODO another cheat
    setTimeout(() => {
      this.farmerInfo.patchValue({
        address: {
          wardId: this.relative.wardId
        }
      });
    }, 500);
  }

  saveFarmerRelative() {
    this.relative = this.prepareData();
    this.farmerRelativeService.saveFarmerRelative(this.relative, this.farmerId)
      .catch(
        (response: Response) => {
          this.notification.notify('Có lỗi xảy ra.');
          return Observable.throw(response);
        }
      )
      .subscribe(
        () => {
          this.notification.notify('Lưu thành công.');
          this.dialogRef.close(true);
        }
      );
  }

  private prepareData(): FarmerRelative {
    const formModel = this.farmerInfo.value;
    const relative = new FarmerRelative();
    relative.id = formModel.id;
    relative.firstName = formModel.firstName;
    relative.lastName = formModel.lastName;
    relative.identityCard = formModel.identityCard;
    relative.phone = formModel.phone;
    relative.email = formModel.email;
    relative.address = this.address.address;
    relative.provinceId = this.address.provinceId;
    relative.districtId = this.address.districtId;
    relative.wardId = this.address.wardId;
    relative.relationship = formModel.relationship;
    if (this.relative) {
      relative.uId = this.relative.uId;
    }
    return relative;
  }

  private buildForm() {
    this.farmerInfo = this.fb.group({
      id: [null],
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      identityCard: [null, Validators.pattern(/^\d+$/)],
      phone: [null, Validators.pattern(/^\d+$/)],
      email: null,
      relationship: [null, Validators.required],
      address: this.fb.group({
        street: [''],
        provinceId: [''],
        districtId: [''],
        wardId: ['']
      })
    });

    this.farmerInfo.valueChanges.subscribe(() => this.onValueChanges());
  }

  private onValueChanges() {
    if (!this.farmerInfo) {
      return;
    }
    const form = this.farmerInfo;

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
