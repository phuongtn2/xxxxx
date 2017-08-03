import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule, MdDialogRef } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Observable } from 'rxjs/Observable';

import { CompanyService } from '../../core/services/company.service';
import { LocationService } from '../../core/services/location.service';
import { NotificationService } from '../../core/services/notification.service';
import { ZoneService } from '../../core/services/zone.service';
import { ObserverZoneDialogComponent } from './observer-zone-dialog.component';

const companyServiceStub = {
  getCompanies: () => Observable.of([])
};
const locationServiceStub = {
  getProvinces: () => Observable.of([]),
  getAllDistricts: () => Observable.of([])
};
const notificationServiceStub = {};
const zoneServiceStub = {
  getZones: () => Observable.of([])
};

describe('ObserverZoneDialogComponent', () => {
  let component: ObserverZoneDialogComponent;
  let fixture: ComponentFixture<ObserverZoneDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ObserverZoneDialogComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        NoopAnimationsModule,
        ReactiveFormsModule
      ],
      providers: [
        { provide: MdDialogRef, useValue: {} },
        { provide: NotificationService, useValue: notificationServiceStub }
      ]
    })
    .overrideComponent(ObserverZoneDialogComponent, {
      set: {
        providers: [
          { provide: CompanyService, useValue: companyServiceStub },
          { provide: LocationService, useValue: locationServiceStub },
          { provide: ZoneService, useValue: zoneServiceStub }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ObserverZoneDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
