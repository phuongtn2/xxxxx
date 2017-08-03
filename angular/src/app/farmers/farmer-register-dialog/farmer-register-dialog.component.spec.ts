import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule, MdDialogRef } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Observable } from 'rxjs/Observable';

import { FarmerRegisterDialogComponent } from './farmer-register-dialog.component';
import { LocationModule } from '../../location/location.module';
import { NotificationService } from '../../core/services/notification.service';
import { LocationService } from '../../core/services/location.service';
import { FarmerService } from '../../core/services/farmer.service';
import { ZoneService } from '../../core/services/zone.service';

describe('FarmerRegisterDialogComponent', () => {
  let component: FarmerRegisterDialogComponent;
  let fixture: ComponentFixture<FarmerRegisterDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FarmerRegisterDialogComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        NoopAnimationsModule,
        ReactiveFormsModule,
        LocationModule
      ],
      providers: [
        { provide: MdDialogRef, useValue: {} },
        NotificationService
      ]
    })
    .overrideComponent(FarmerRegisterDialogComponent, {
      set: {
        providers: [
          { provide: LocationService, useValue: {
            getProvinces: () => Observable.of([])
          } },
          { provide: FarmerService, useValue: {} },
          { provide: ZoneService, useValue: {} }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FarmerRegisterDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
