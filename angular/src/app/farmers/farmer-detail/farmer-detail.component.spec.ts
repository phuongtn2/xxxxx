import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { Observable } from 'rxjs/Observable';

import { FarmerDetailComponent } from './farmer-detail.component';
import { DialogService } from '../../core/services/dialog.service';
import { NotificationService } from '../../core/services/notification.service';
import { FarmerRelativesModule } from '../../farmer-relatives/farmer-relatives.module';
import { LocationModule } from '../../location/location.module';
import { LocationService } from '../../core/services/location.service';
import { FarmerService } from '../../core/services/farmer.service';
import { Farmer } from '../../core/models/farmer';
import { HttpService } from '../../core/services/http.service';

describe('FarmerDetailComponent', () => {
  let component: FarmerDetailComponent;
  let fixture: ComponentFixture<FarmerDetailComponent>;

  beforeEach(async(() => {
    const mockFarmer = new Farmer();
    mockFarmer.id = 0;
    mockFarmer.farmerCode = '';
    mockFarmer.firstName = '';
    mockFarmer.lastName = '';
    mockFarmer.phone = '';
    mockFarmer.email = '';
    mockFarmer.address = '';
    mockFarmer.provinceId = 0;
    mockFarmer.districtId = 0;
    mockFarmer.wardId = 0;
    TestBed.configureTestingModule({
      declarations: [ FarmerDetailComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        NoopAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        FarmerRelativesModule,
        LocationModule
      ],
      providers: [
        DialogService,
        NotificationService,
        { provide: HttpService, useValue: {} }
      ]
    })
    .overrideComponent(FarmerDetailComponent, {
      set: {
        providers: [
          { provide: LocationService, useValue: {
            getProvinces: () => Observable.of([]),
            getDistricts: (provinceId: number) => Observable.of([]),
            getWards: (districtId: number) => Observable.of([])
          } },
          { provide: FarmerService, useValue: {
            get: (farmerId: number) => Observable.of(mockFarmer)
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FarmerDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
