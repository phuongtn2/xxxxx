import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule, MdDialogRef } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Observable } from 'rxjs/Observable';

import { FieldDetailDialogComponent } from './field-detail-dialog.component';
import { LocationModule } from '../../location/location.module';
import { LocationService } from '../../core/services/location.service';
import { MaterialService } from '../../materials/material.service';
import { NotificationService } from '../../core/services/notification.service';
import { FieldService } from '../field.service';

describe('FieldDetailDialogComponent', () => {
  let component: FieldDetailDialogComponent;
  let fixture: ComponentFixture<FieldDetailDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FieldDetailDialogComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        NoopAnimationsModule,
        ReactiveFormsModule,
        LocationModule
      ],
      providers: [
        { provide: MdDialogRef, useValue: {} },
        NotificationService,
        { provide: FieldService, useValue: {} },
        { provide: LocationService, useValue: {
          getProvinces: () => Observable.of([])
        } }

      ]
    })
    .overrideComponent(FieldDetailDialogComponent, {
      set: {
        providers: [
          { provide: MaterialService, useValue: {
            getSeeds: () => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FieldDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
