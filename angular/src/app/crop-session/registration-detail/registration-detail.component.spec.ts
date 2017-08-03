import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { MdDataTableModule } from '@dtrthi/md-data-table';
import { Observable } from 'rxjs/Observable';

import { LocationModule } from '../../location/location.module';
import { RegistrationDetailComponent } from './registration-detail.component';
import { SaleListComponent } from '../sale-list/sale-list.component';
import { FieldCardComponent } from '../../fields/field-card/field-card.component';
import { NurseryCardComponent } from '../../nurseries/nursery-card/nursery-card.component';
import { CropSessionService } from '../crop-session.service';
import { CropRegisterService } from '../registration.service';
import { NotificationService } from '../../core/services/notification.service';
import { ZoneService } from '../../core/services/zone.service';
import { FieldService } from '../../fields/field.service';
import { FarmerService } from '../../core/services/farmer.service';
import { LocationService } from '../../core/services/location.service';
import { NurseryService } from '../../nurseries/nursery.service';
import { UserService } from '../../users/user.service';
import { HttpService } from '../../core/services/http.service';
import { DryingOvenListComponent } from '../drying-oven-list/drying-oven-list.component';
import { CropMaterialListComponent } from '../crop-material-list/crop-material-list.component';
import { MaterialService } from '../../materials/material.service';

describe('RegistrationDetailComponent', () => {
  let component: RegistrationDetailComponent;
  let fixture: ComponentFixture<RegistrationDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CropMaterialListComponent,
        DryingOvenListComponent,
        FieldCardComponent,
        NurseryCardComponent,
        RegistrationDetailComponent,
        SaleListComponent
      ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        MdDataTableModule,
        NoopAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        LocationModule
      ],
      providers: [
        { provide: FieldService, useValue: {} },
        { provide: NurseryService, useValue: {} },
        { provide: NotificationService, useValue: {} },
        { provide: HttpService, useValue: {} },
        { provide: MaterialService, useValue: {
          getSeeds: () => Observable.of([])
        } }
      ]
    })
    .overrideComponent(RegistrationDetailComponent, {
      set: {
        providers: [
          { provide: UserService, useValue: {
            getResponsibilityEmployees: () => Observable.of([])
          } },
          { provide: FarmerService, useValue: {
            getUnregisteredFarmers: () => Observable.of([])
          } },
          { provide: CropSessionService, useValue: {} },
          { provide: CropRegisterService, useValue: {} },
          { provide: ZoneService, useValue: {
            getZones: () => Observable.of([])
          } },
          { provide: LocationService, useValue: {
            getProvinces: () => Observable.of([]),
            getAllDistricts: () => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
