import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { LocationService } from '../../core/services/location.service';
import { User } from '../../core/models/user';
import { CompanyService } from '../../core/services/company.service';
import { ObserverService } from '../../core/services/observer.service';
import { ZoneService } from '../../core/services/zone.service';
import { ObserverZoneListComponent } from './observer-zone-list.component';

const companyServiceStub = {
  getCompanies: () => Observable.of([])
};
const locationServiceStub = {
  getProvinces: () => Observable.of([]),
  getAllDistricts: () => Observable.of([])
};
const observerServiceStub = {
  getByEmployeeId: (employeeId) => Observable.of([])
};
const zoneServiceStub = {
  getZones: () => Observable.of([])
};

describe('ObserverZoneListComponent', () => {
  let component: ObserverZoneListComponent;
  let fixture: ComponentFixture<ObserverZoneListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ObserverZoneListComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule
      ],
      providers: [
        { provide: LocationService, useValue: locationServiceStub },
        { provide: ObserverService, useValue: observerServiceStub }
      ]
    })
    .overrideComponent(ObserverZoneListComponent, {
      set: {
        providers: [
          { provide: CompanyService, useValue: companyServiceStub },
          { provide: ZoneService, useValue: zoneServiceStub },
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ObserverZoneListComponent);
    component = fixture.componentInstance;
    component.user = new User();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
