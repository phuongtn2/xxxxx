import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Response } from '@angular/http';
import { MdDialog } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/zip';

import { FieldService } from '../../fields/field.service';
import { NurseryService } from '../../nurseries/nursery.service';
import { Farmer } from '../../core/models/farmer';
import { User } from '../../core/models/user';
import { Registration } from '../registration';
import { CropRegisterService } from '../registration.service';
import { FarmerService } from '../../core/services/farmer.service';
import { UserService } from '../../users/user.service';
import { NotificationService } from '../../core/services/notification.service';
import { FarmerRegisterDialogComponent } from '../../farmers/farmer-register-dialog/farmer-register-dialog.component';
import { CropSessionService } from '../crop-session.service';
import { ZoneService } from '../../core/services/zone.service';
import { Zone } from '../../core/models/zone';
import { MaterialService } from '../../materials/material.service';
import { Material } from '../../materials/material';
import { Field } from '../../fields/field';
import { Nursery } from '../../nurseries/nursery';

@Component({
  selector: 'app-registration-detail',
  templateUrl: './registration-detail.component.html',
  styleUrls: ['./registration-detail.component.scss'],
  providers: [
    UserService,
    MaterialService,
    FarmerService,
    CropSessionService,
    CropRegisterService,
    ZoneService
  ]
})
export class RegistrationDetailComponent implements OnInit {
  title: string;
  private _registration: Registration;
  isEditing: boolean;

  get registration(): Registration {
    return this._registration;
  }

  set registration(value: Registration) {
    this._registration = value;
    if (this.registration.cultivationId) {
      this.fieldService.getField(this.registration.id, this.registration.cultivationId)
        .subscribe(
          field => {
            this._registration.field = field;
          }
        );
    }
    if (this.registration.nurseryId) {
      this.nurseryService.getNursery(this.registration.id, this.registration.nurseryId)
        .subscribe(
          nursery => {
            this._registration.nursery = nursery;
          }
        );
    }
    this.setFormData();
  }

  registerForm: FormGroup;
  farmers: Farmer[];
  responsibilityEmployees: User[];

  private zones: Zone[];

  get selectedFarmer() {
    if (this._registration && this._registration.farmer) {
      return this._registration.farmer;
    }
    return this.farmers.find(farmer => farmer.id === this.registerForm.get('farmerId').value);
  }

  seeds: Material[];

  formErrors = {
    farmerId: '',
    exp: '',
    householdMembers: '',
    labors: '',
    registrationAcreage: '',
    responsibilityEmployeeId: '',
    zoneId: ''
  };

  private validationMessages = {
    farmerId: {
      required: 'Cần chọn Nông dân.'
    },
    exp: {
      required: 'Cần nhập Số năm trồng.'
    },
    householdMembers: {
      required: 'Cần nhập Số nhân khẩu.'
    },
    labors: {
      required: 'Cần nhập Số lao động chính.'
    },
    registrationAcreage: {
      required: 'Cần nhập Diện tích đăng ký.'
    },
    responsibilityEmployeeId: {
      required: 'Cần chọn Mật Khẩu.'
    },
    zoneId: {
      required: 'Cần chọn Mật Khẩu.'
    }
  };

  constructor(
    private titleService: Title,
    private dialog: MdDialog,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private materialService: MaterialService,
    private farmerService: FarmerService,
    private cropSessionService: CropSessionService,
    private fieldService: FieldService,
    private nurseryService: NurseryService,
    private registrationService: CropRegisterService,
    private notification: NotificationService,
    private zoneService: ZoneService
  ) {
    this.title = 'Đăng Ký Nông Dân';
    this.isEditing = false;
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.getZones();
    this.getSeeds();
    // get data
    this.userService.getResponsibilityEmployees()
      .subscribe(
        (employees: User[]) => {
          this.responsibilityEmployees = employees;
        }
      );
    this.getFarmerList();
    this.buildForm();

    // get and fill register data if view/edit
    const farmerCode = this.route.snapshot.params['farmerCode'];
    if (farmerCode) {
      this.cropSessionService.getRegistrationByFarmerCode(farmerCode)
        .subscribe(
          (registration: Registration) => {
            this.registration = registration;
          }
        );
    }
  }

  /**
   * Get company zones only.
   */
  getZones() {
    this.zoneService.getZones().subscribe(
      zones => {
        this.zones = zones;
      }
    );
  }

  getSeeds() {
    this.materialService.getSeeds()
      .subscribe(
        (seeds) => {
          this.seeds = seeds;
        }
      );
  }

  openNewFarmerModal() {
    const dialogRef = this.dialog.open(FarmerRegisterDialogComponent);
    dialogRef.afterClosed().subscribe(
      (isAdded) => {
        // refresh farmer list
        if (isAdded) {
          this.getFarmerList();
        }
      }
    );
  }

  cancelRegistration() {
    this.registrationService.cancel(this.registration)
      .subscribe(
        () => {
          this.notification.notify(`Đã hủy đăng ký nông dân ${this._registration.farmer.fullName}.`);
          this.router.navigate(['crop-session', 'farmer']);
        }
      );
  }

  registerAgainRegistration() {
    this.registrationService.registerAgain(this.registration)
      .subscribe(
        () => {
          this.notification.notify(`Đăng ký lại nông dân ${this._registration.farmer.fullName}.`);
          this.registerForm.enable();
        }
      );
  }

  saveFarmer() {
    // require at least 1 field or nursery been registered
    if (this.registerForm.get('fieldDetail').disabled && this.registerForm.get('nurseryDetail').disabled) {
      this.notification.notify('Need to register field or nursery.');
      return;
    }
    if (!this.registerForm.valid) {
      return;
    }

    this.prepareRegisterData();
    this.registrationService.save(this._registration)
      .catch(
        (response: Response) => {
          this.notification.notify('Có lỗi xảy ra.');

          return Observable.throw(response);
        }
      )
      .subscribe(
        (registration: Registration) => {
          this.registration = registration;

          let registerFieldObs = Observable.of(null);
          let registerNurseryObs = Observable.of(null);
          if (this.registerForm.get('fieldDetail').enabled) {
            registerFieldObs = this.registerField(registration.id);
          }
          if (this.registerForm.get('nurseryDetail').enabled) {
            registerNurseryObs = this.registerNursery(registration.id);
          }
          Observable.zip(
            registerFieldObs,
            registerNurseryObs,
            () => {
              this.notification.notify(`Đã lưu thông tin nông dân ${this.selectedFarmer.fullName}.`);
              this.router.navigate(['crop-session', 'farmer']);
            }
          ).subscribe();
        }
      );
  }

  private prepareRegisterData() {
    if (!this._registration) {
        this._registration = new Registration();
    }
    const formModel = this.registerForm.value;
    this._registration.id = formModel.id;
    this._registration.farmerId = formModel.farmerId;
    this._registration.exp = formModel.exp;
    this._registration.householdMembers = formModel.householdMembers;
    this._registration.labors = formModel.labors;
    this._registration.memo = formModel.memo;
    this._registration.registrationAcreage = formModel.registrationAcreage;
    this._registration.responsibilityEmployeeId = formModel.responsibilityEmployeeId;
    this._registration.zoneId = formModel.zoneId;
  }

  private getFarmerList() {
    this.farmerService.getUnregisteredFarmers()
      .subscribe(
        (farmers: Farmer[]) => {
          this.farmers = farmers;
        }
      );
  }

  private setFormData() {
    this.registerForm.patchValue({
      id: this._registration.id,
      farmerId: this._registration.farmerId,
      exp: this._registration.exp,
      householdMembers: this._registration.householdMembers,
      labors: this._registration.labors,
      memo: this._registration.memo,
      registrationAcreage: this._registration.registrationAcreage,
      responsibilityEmployeeId: this._registration.responsibilityEmployeeId,
      zoneId: this._registration.zoneId
    });
    this.isEditing = true;

    if (this._registration.isCancel) {
      this.registerForm.disable();
    }
  }

  private buildForm() {
    this.registerForm = this.fb.group({
      id: [null],
      farmerId: [null, Validators.required],
      exp: [null, Validators.required],
      householdMembers: [null, Validators.required],
      labors: [null, Validators.required],
      memo: [null],
      registrationAcreage: [null, Validators.required],
      responsibilityEmployeeId: [null, Validators.required],
      zoneId: [null, Validators.required],
      // field register form
      toggleRegisterField: false,
      fieldDetail: this.fb.group({
        address: this.fb.group({
          street: ['', Validators.required],
          provinceId: ['', Validators.required],
          districtId: ['', Validators.required],
          wardId: ['', Validators.required]
        }),
        seedId: ['', Validators.required]
      }),
      // nursery register form
      toggleRegisterNursery: false,
      nurseryDetail: this.fb.group({
        address: this.fb.group({
          street: ['', Validators.required],
          provinceId: ['', Validators.required],
          districtId: ['', Validators.required],
          wardId: ['', Validators.required]
        }),
        seedId: ['', Validators.required]
      })
    });
    this.registerForm.get('fieldDetail').disable();
    this.registerForm.get('nurseryDetail').disable();

    this.registerForm.valueChanges
      .subscribe(() => this.onValueChanges());

    this.registerForm.get('toggleRegisterField').valueChanges.subscribe(
      (toggled) => {
        const fieldDetail = this.registerForm.get('fieldDetail');
        if (toggled) {
          fieldDetail.enable();
        } else {
          fieldDetail.reset();
          fieldDetail.disable();
        }
      }
    );
    this.registerForm.get('toggleRegisterNursery').valueChanges.subscribe(
      (toggled) => {
        const nurseryDetail = this.registerForm.get('nurseryDetail');
        if (toggled) {
          nurseryDetail.enable();
        } else {
          nurseryDetail.reset();
          nurseryDetail.disable();
        }
      }
    );

    this.registerForm.get('fieldDetail.address').valueChanges.subscribe(
      (value) => {
        this.registerForm.get('zoneId').reset();
        if (value.provinceId && value.districtId) {
          const cultivationZone = this.findCultivationZone(value.provinceId, value.districtId);
          if (cultivationZone) {
            this.registerForm.get('zoneId').setValue(cultivationZone.id);
          }
        }
      }
    );
    this.registerForm.get('nurseryDetail.address').valueChanges.subscribe(
      (value) => {
        if (this.registerForm.get('fieldDetail').disabled) {
          this.registerForm.get('zoneId').reset();
          if (value.provinceId && value.districtId) {
            const cultivationZone = this.findCultivationZone(value.provinceId, value.districtId);
            if (cultivationZone) {
              this.registerForm.get('zoneId').setValue(cultivationZone.id);
            }
          }
        }
      }
    );
  }

  private onValueChanges() {
    if (!this.registerForm) {
      return;
    }
    const form = this.registerForm;

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

  private findCultivationZone(provinceId, districtId) {
    return this.zones.find(zone => zone.provinceId === provinceId && zone.districtId === districtId);
  }

  /// ======================================================
  // Field registration
  /// ------------------------------------------------------
  registerField(registrationId) {
    const field = this.prepareFieldData();
    return this.fieldService.register(registrationId, field)
      .catch(
        () => {
          this.notification.notify('Lỗi khi đăng ký ruộng.');
          return Observable.empty();
        }
      )
      .map(
        (newField) => {
          this.notification.notify('Đã tạo ruộng.');
          return newField;
        }
      );
  }

  private prepareFieldData() {
    const formModel = this.registerForm.get('fieldDetail').value;
    const field = new Field();
    field.address = formModel.address.street;
    field.provinceId = formModel.address.provinceId;
    field.districtId = formModel.address.districtId;
    field.wardId = formModel.address.wardId;
    field.seedId = formModel.seedId;

    return field;
  }

  /// ======================================================
  // Nursery registration
  /// ------------------------------------------------------
  registerNursery(registrationId) {
    const nursery = this.prepareNurseryData();
    return this.nurseryService.register(registrationId, nursery)
      .catch(
        () => {
          this.notification.notify('Lỗi khi đăng ký vườn.');
          return Observable.empty();
        }
      )
      .map(
        (newNursery) => {
          this.notification.notify('Đã tạo vườn.');
          return newNursery;
        }
      );
  }

  private prepareNurseryData() {
    const formModel = this.registerForm.get('nurseryDetail').value;
    const nursery = new Nursery();
    nursery.address = formModel.address.street;
    nursery.provinceId = formModel.address.provinceId;
    nursery.districtId = formModel.address.districtId;
    nursery.wardId = formModel.address.wardId;
    nursery.seedId = formModel.seedId;

    return nursery;
  }
}
