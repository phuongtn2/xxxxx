import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';
import { MdDataTableModule } from '@dtrthi/md-data-table';
import { Observable } from 'rxjs/Observable';

import { FieldDetailComponent } from './field-detail.component';
import { FieldService } from '../field.service';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { MaterialService } from '../../materials/material.service';
import { LocationService } from '../../core/services/location.service';
import { Registration } from '../../crop-session/registration';
import { Farmer } from '../../core/models/farmer';
import { User } from '../../core/models/user';
import { LocationModule } from '../../location/location.module';

describe('FieldDetailComponent', () => {
  let component: FieldDetailComponent;
  let fixture: ComponentFixture<FieldDetailComponent>;

  beforeEach(async(() => {
    const mockRegistration = new Registration();
    mockRegistration.farmer = new Farmer();
    mockRegistration.responsibilityEmployee = new User();
    TestBed.configureTestingModule({
      declarations: [ FieldDetailComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        MdDataTableModule,
        RouterTestingModule,
        LocationModule
      ],
      providers: [
        { provide: FieldService, useValue: {
          getField: (registrationId: any, fieldId: any) => Observable.of([])
        } }
      ]
    })
    .overrideComponent(FieldDetailComponent, {
      set: {
        providers: [
          { provide: CropSessionService, useValue: {
            getRegistrationByFarmerCode: (farmerCode: string) => Observable.of(mockRegistration)
          } },
          { provide: MaterialService, useValue: {
            get: (id: number) => Observable.of(null)
          } },
          { provide: LocationService, useValue: {
            getProvince: (provinceId: number) => Observable.of(null)
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FieldDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
