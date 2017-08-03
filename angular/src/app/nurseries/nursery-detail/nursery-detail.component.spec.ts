import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';
import { MdDataTableModule } from '@dtrthi/md-data-table';
import { Observable } from 'rxjs/Observable';

import { NurseryDetailComponent } from './nursery-detail.component';
import { NurseryService } from '../nursery.service';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { MaterialService } from '../../materials/material.service';
import { LocationService } from '../../core/services/location.service';
import { Nursery } from '../nursery';
import { Registration } from '../../crop-session/registration';
import { Farmer } from '../../core/models/farmer';
import { User } from '../../core/models/user';
import { LocationModule } from '../../location/location.module';

describe('NurseryDetailComponent', () => {
  let component: NurseryDetailComponent;
  let fixture: ComponentFixture<NurseryDetailComponent>;

  beforeEach(async(() => {
    const mockRegistration = new Registration();
    mockRegistration.farmer = new Farmer();
    mockRegistration.responsibilityEmployee = new User();
    TestBed.configureTestingModule({
      declarations: [ NurseryDetailComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        MdDataTableModule,
        RouterTestingModule,
        LocationModule
      ],
      providers: [
        { provide: NurseryService, useValue: {
          getNursery: (registrationId: number, nurseryId: number) => Observable.of(new Nursery())
        } },
      ]
    })
    .overrideComponent(NurseryDetailComponent, {
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
    fixture = TestBed.createComponent(NurseryDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
