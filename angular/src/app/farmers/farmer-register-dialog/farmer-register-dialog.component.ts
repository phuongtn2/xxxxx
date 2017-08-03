import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Response } from '@angular/http';
import { MdDialogRef } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/zip';

import { Farmer } from '../../core/models/farmer';
import { District, Province, Ward } from '../../core/models/location';
import { FarmerService } from '../../core/services/farmer.service';
import { LocationService } from '../../core/services/location.service';
import { NotificationService } from '../../core/services/notification.service';
import { ZoneService } from '../../core/services/zone.service';

@Component({
  selector: 'app-farmer-register-dialog',
  templateUrl: './farmer-register-dialog.component.html',
  styleUrls: ['./farmer-register-dialog.component.scss'],
  providers: [FormBuilder, LocationService, FarmerService, ZoneService]
})
export class FarmerRegisterDialogComponent implements OnInit {
  title: string;
  provinces: Province[];
  districts: District[];
  wards: Ward[];
  farmerInfo: FormGroup;
  farmer: Farmer;
  private isEditing: boolean;

  formErrors = {
    farmerCode: '',
    firstName: '',
    lastName: '',
    phone: ''
  };

  private validationMessages = {
    farmerCode: {
      required: 'Cần nhập Mã ND.'
    },
    firstName: {
      required: 'Cần nhập Họ và chữ lót.'
    },
    lastName: {
      required: 'Cần nhập Tên.'
    },
    phone: {
      required: 'Cần nhập Số điện thoại.',
      pattern: 'Số điện thoại không hợp lệ.'
    }
  };

  constructor(
    private titleService: Title,
    private dialogRef: MdDialogRef<FarmerRegisterDialogComponent>,
    private fb: FormBuilder,
    private farmerService: FarmerService,
    private locationService: LocationService,
    private notification: NotificationService
  ) {
    this.title = 'Đăng Ký Nông Dân';
    this.isEditing = false;
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);

    // load location options for select boxes
    this.buildForm();
    if (this.farmer) {
      this.setFormData();
    }
  }

  saveFarmer() {
    this.farmer = this.prepareFarmer();
    this.farmerService.save(this.farmer)
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

  private prepareFarmer(): Farmer {
    const formModel = this.farmerInfo.value;
    const farmer = new Farmer();
    farmer.id = formModel.id;
    farmer.farmerCode = formModel.farmerCode;
    farmer.firstName = formModel.firstName;
    farmer.lastName = formModel.lastName;
    farmer.phone = formModel.phone;
    farmer.email = formModel.email;
    farmer.address = formModel.address.street;
    farmer.provinceId = formModel.address.provinceId;
    farmer.districtId = formModel.address.districtId;
    farmer.wardId = formModel.address.wardId;
    return farmer;
  }

  private setFormData() {
    const ob1 = this.locationService.getDistricts(this.farmer.provinceId);
    const ob2 = this.locationService.getWards(this.farmer.districtId);
    Observable.zip(ob1, ob2, (districts, wards) => {
      this.districts = districts;
      this.wards = wards;
    }).subscribe(
      () => {
        this.farmerInfo.setValue({
          id: this.farmer.id,
          farmerCode: this.farmer.farmerCode,
          firstName: this.farmer.firstName,
          lastName: this.farmer.lastName,
          phone: this.farmer.phone,
          email: this.farmer.email,
          address: {
            street: this.farmer.address,
            provinceId: this.farmer.provinceId,
            districtId: this.farmer.districtId,
            wardId: this.farmer.wardId
          }
        });
        // TODO another cheat
        setTimeout(() => {
          this.farmerInfo.patchValue({
            address: {
              wardId: this.farmer.wardId
            }
          });
        }, 500);
      }
    );
    this.isEditing = true;
    this.farmerInfo.get('farmerCode').disable();
  }

  private buildForm() {
    this.farmerInfo = this.fb.group({
      id: [null],
      farmerCode: [null, Validators.required],
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      phone: [null, [Validators.required, Validators.pattern(/^\d+$/)]],
      email: [null],
      address: this.fb.group({
        street: [null, Validators.required],
        provinceId: ['', Validators.required],
        districtId: ['', Validators.required],
        wardId: ['', Validators.required]
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
