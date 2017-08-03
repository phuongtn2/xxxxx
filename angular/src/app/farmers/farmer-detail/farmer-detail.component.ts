import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Response } from '@angular/http';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';
import 'rxjs/add/observable/zip';

import { Farmer } from '../../core/models/farmer';
import { District, Province, Ward } from '../../core/models/location';
import { FarmerService } from '../../core/services/farmer.service';
import { LocationService } from '../../core/services/location.service';
import { NotificationService } from '../../core/services/notification.service';

@Component({
  selector: 'app-farmer-detail',
  templateUrl: './farmer-detail.component.html',
  styleUrls: ['./farmer-detail.component.scss'],
  providers: [FormBuilder, LocationService, FarmerService]
})
export class FarmerDetailComponent implements OnInit {
  title: string;
  provinces: Province[];
  wards: Ward[];
  districts: District[];
  farmerInfo: FormGroup;
  farmer: Farmer;

  isEditing = false;

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
    private fb: FormBuilder,
    private farmerService: FarmerService,
    private notification: NotificationService,
    private router: Router,
    private route: ActivatedRoute,
    private locationService: LocationService
  ) { }


  ngOnInit() {
    this.title = 'Thêm nông dân';
    this.titleService.setTitle(this.title);

    this.buildForm();
    const id = +this.route.snapshot.params['id'];
    this.farmerService.get(id)
      .subscribe(
        (farmer: Farmer) => {
          if (farmer instanceof ErrorObservable) {
            return false;
          }
          this.farmer = farmer;
          this.setFormData();
        }
      );
  }

  saveFarmerDetail() {
    this.farmer = this.prepareFarmerDetail();
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
          this.router.navigate(['/farmers']);
        }
      );
  }

  private prepareFarmerDetail(): Farmer {
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
    this.title = 'Chi tiết nông dân';
    this.titleService.setTitle(this.title);
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
