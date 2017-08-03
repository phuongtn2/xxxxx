import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { Observable } from 'rxjs/Observable';

import { CropUserRegisterComponent } from './crop-user-register.component';
import { AuthenticationService } from '../../core/services/authentication.service';
import { NotificationService } from '../../core/services/notification.service';
import { LocationModule } from '../../location/location.module';
import { TechnicianZonesModule } from '../technician-zones/technician-zones.module';
import { ObserverZonesModule } from '../../observer-zones/observer-zones.module';
import { LocationService } from '../../core/services/location.service';
import { UserService } from '../user.service';
import { User } from '../../core/models/user';
import { RoleSlug } from '../../core/enum/role-slug.enum';

describe('CropUserRegisterComponent', () => {
  let component: CropUserRegisterComponent;
  let fixture: ComponentFixture<CropUserRegisterComponent>;

  beforeEach(async(() => {
    const user = new User();
    user.id = 0;
    user.companyId = 0;
    user.firstName = '';
    user.lastName = '';
    user.phone = '';
    user.email = '';
    user.departmentId = 0;
    user.divisionId = 0;
    user.userId = '';
    user.password = '';
    user.roleSlug = RoleSlug.User;
    user.position = '';
    user.employeeCode = '';
    user.address = '';
    user.provinceId = 0;
    user.districtId = 0;
    user.wardId = 0;
    TestBed.configureTestingModule({
      declarations: [
        CropUserRegisterComponent
      ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        NoopAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        LocationModule,
        ObserverZonesModule,
        TechnicianZonesModule
      ],
      providers: [
        { provide: AuthenticationService, useValue: {} },
        { provide: NotificationService, useValue: {} }
      ]
    })
    .overrideComponent(CropUserRegisterComponent, {
      set: {
        providers: [
          { provide: LocationService, useValue: {
            getProvinces: () => Observable.of([]),
            getDistricts: () => Observable.of([]),
            getWards: () => Observable.of([])
          } },
          { provide: UserService, useValue: {
            get: (userId: number) => Observable.of(user),
            getDepartments: () => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CropUserRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
