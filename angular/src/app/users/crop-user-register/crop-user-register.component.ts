import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Response } from '@angular/http';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';
import 'rxjs/add/observable/zip';

import { RoleSlugs } from '../../core/enum/role-slug.enum';
import { User } from '../../core/models/user';
import { LocationService } from '../../core/services/location.service';
import { District, Province, Ward } from '../../core/models/location';
import { NotificationService } from '../../core/services/notification.service';
import { UserService } from '../user.service';
import { Division } from '../../core/models/division';
import { Department } from '../department';
import { AuthenticationService } from '../../core/services/authentication.service';

@Component({
  selector: 'app-crop-user-register',
  templateUrl: './crop-user-register.component.html',
  styleUrls: ['./crop-user-register.component.scss'],
  providers: [FormBuilder, LocationService, UserService]
})
export class CropUserRegisterComponent implements OnInit {
  title: string;
  user: User;
  users: User[];
  districts: District[];
  wards: Ward[];
  userInfo: FormGroup;
  departments: Array<Department>;
  divisions: Division[];
  isEditing: boolean;
  position: string;

  private _slugs;
  get roleSlugs() {
    if (!this._slugs) {
      this._slugs = RoleSlugs(this.authService.currentUser.roleSlug);
    }
    return this._slugs;
  }

  formErrors = {
    firstName: '',
    lastName: '',
    phone: '',
    email: '',
    departmentId: '',
    divisionId: '',
    userId: '',
    password: '',
    roleSlug: '',
    position: '',
    employeeCode: ''
  };

  private validationMessages = {
    firstName: {
      required: 'Cần nhập Họ.'
    },
    lastName: {
      required: 'Cần nhập Tên.'
    },
    phone: {
      required: 'Cần nhập Số điện thoại.',
      pattern: 'Số điện thoại không hợp lệ.'
    },
    email: {
      required: 'Cần nhập Email.'
    },
    departmentId: {
      required: 'Cần nhập Phòng.'
    },
    divisionId: {
      required: 'Cần nhập Tổ.'
    },
    userId: {
      required: 'Cần nhập Tài khoản.'
    },
    password: {
      required: 'Cần nhập Mật khẩu.'
    },
    roleSlug: {
      required: 'Cần nhập Chức vụ.'
    },
    position: {
      required: 'Cần nhập Chức vụ.'
    },
    employeeCode: {
      required: 'Cần nhập Mã nhân viên.'
    }
  };

  constructor(
    private titleService: Title,
    private userService: UserService,
    private fb: FormBuilder,
    private locationService: LocationService,
    private notification: NotificationService,
    private route: ActivatedRoute,
    private router: Router,
    public authService: AuthenticationService,
  ) {
    this.isEditing = false;
  }

  ngOnInit() {
    this.title = 'Đăng ký nhân sự';
    this.titleService.setTitle(this.title);
    this.buildForm();

    const id = +this.route.snapshot.params['id'];
    this.userService.get(id)
      .subscribe(
        (employee: User) => {
          // TODO fix this
          if (employee instanceof ErrorObservable) {
            return false;
          }
          this.user = employee;
          this.setFormData();
        }
      );

    this.getDepartments();
  }

  getDepartments() {
    this.userService.getDepartments().subscribe(
      departments => {
        this.departments = departments;
      }
    );
  }

  onSelectDepartment(departmentId) {
    this.userInfo.patchValue({divisionId: ''});
    return this.userService.getDivisions(departmentId)
      .subscribe(
        divisions => {
          this.divisions = divisions;
        }
      );
  }

  getRoleSlugPosition(key): string {
    return this.roleSlugs.find(slug => slug.key.value === key).value;
  }

  private prepareUser(): User {
    const formModel = this.userInfo.value;
    const user = new User();
    user.id = formModel.id;
    user.companyId = formModel.companyId;
    user.firstName = formModel.firstName;
    user.lastName = formModel.lastName;
    user.phone = formModel.phone;
    user.email = formModel.email;
    user.departmentId = formModel.departmentId;
    user.divisionId = formModel.divisionId;
    user.address = formModel.address.street;
    user.provinceId = formModel.address.provinceId;
    user.districtId = formModel.address.districtId;
    user.wardId = formModel.address.wardId;
    user.userId = formModel.userId;
    user.password = formModel.password;
    if (!this.isEditing) { // TODO(dtrthi): check other disable form controls
      user.roleSlug = formModel.roleSlug;
      user.position = this.getRoleSlugPosition(formModel.roleSlug);
    }
    user.employeeCode = formModel.employeeCode;
    return user;
  }

  private setFormData() {
    const ob1 = this.locationService.getDistricts(this.user.provinceId);
    const ob2 = this.locationService.getWards(this.user.districtId);
    Observable.zip(ob1, ob2, (districts, wards) => {
      this.districts = districts;
      this.wards = wards;
    }).subscribe(
      () => {
        this.userInfo.setValue({
          id: this.user.id,
          companyId: this.user.companyId,
          firstName: this.user.firstName,
          lastName: this.user.lastName,
          phone: this.user.phone,
          email: this.user.email,
          departmentId: this.user.departmentId,
          divisionId: this.user.divisionId,
          userId: this.user.userId,
          password: '',
          roleSlug: this.user.roleSlug,
          position: this.user.position,
          employeeCode: this.user.employeeCode,
          address: {
            street: this.user.address,
            provinceId: this.user.provinceId,
            districtId: this.user.districtId,
            wardId: this.user.wardId
          }
        });
        // TODO another cheat
        setTimeout(() => {
          this.userInfo.patchValue({
            address: {
              wardId: this.user.wardId
            }
          });
        }, 500);
      }
    );
    this.isEditing = true;
    this.title = 'Chi tiết nhân sự';
    this.titleService.setTitle(this.title);
    this.userInfo.get('userId').disable();
    this.userInfo.get('password').disable();
    this.userInfo.get('employeeCode').disable();
    this.userInfo.get('divisionId').disable();
    this.userInfo.get('departmentId').disable();
    this.userInfo.get('roleSlug').disable();
    this.userInfo.get('position').disable();
  }

  saveUser() {
      this.user = this.prepareUser();
      // TODO xoa department khi sua api xong
      this.userService.save(this.user)
        .catch(
          (response: Response) => {
            this.notification.notify('Có lỗi xảy ra.');
            return Observable.throw(response);
          }
        )
        .subscribe(
          () => {
            this.notification.notify('Lưu thành công');
            this.router.navigate(['/users']);
          }
        );
  }

  private buildForm() {
    this.userInfo = this.fb.group({
      id: [''],
      companyId: [''],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      email: ['', [Validators.required]],
      departmentId: ['', Validators.required],
      divisionId: ['', Validators.required],
      userId: ['', Validators.required],
      password: ['', Validators.required],
      roleSlug: ['', Validators.required],
      position: ['', Validators.required],
      employeeCode: ['', Validators.required],
      address: this.fb.group({
        street: ['', Validators.required],
        provinceId: ['', Validators.required],
        districtId: ['', Validators.required],
        wardId: ['', Validators.required]
      })
    });

    this.userInfo.valueChanges
      .subscribe(() => this.onValueChanges());
  }

  private onValueChanges() {
    if (!this.userInfo) {
      return;
    }
    const form = this.userInfo;

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
