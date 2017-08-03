import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule, MdDialogRef } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Observable } from 'rxjs/Observable';

import { NurseryDetailDialogComponent } from './nursery-detail-dialog.component';
import { LocationModule } from '../../location/location.module';
import { NotificationService } from '../../core/services/notification.service';
import { LocationService } from '../../core/services/location.service';
import { MaterialService } from '../../materials/material.service';
import { NurseryService } from '../nursery.service';

describe('NurseryDetailDialogComponent', () => {
  let component: NurseryDetailDialogComponent;
  let fixture: ComponentFixture<NurseryDetailDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NurseryDetailDialogComponent ],
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
        { provide: NurseryService, useValue: {}},
        { provide: LocationService, useValue: {
          getProvinces: () => Observable.of([])
        } }
      ]
    })
    .overrideComponent(NurseryDetailDialogComponent, {
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
    fixture = TestBed.createComponent(NurseryDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
