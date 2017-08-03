import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule, MdDialogRef } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';
import { Observable } from 'rxjs/Observable';

import { TaskDetailDialogComponent } from './task-detail-dialog.component';
import { LocationService } from '../../core/services/location.service';

describe('TaskDetailDialogComponent', () => {
  let component: TaskDetailDialogComponent;
  let fixture: ComponentFixture<TaskDetailDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TaskDetailDialogComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        RouterTestingModule
      ],
      providers: [
        { provide: MdDialogRef, useValue: {} }
      ]
    })
    .overrideComponent(TaskDetailDialogComponent, {
      set: {
        providers: [
          { provide: LocationService, useValue: {
            getProvince: (provinceId: number) => Observable.of([]),
            getDistrict: (districtId: number, provinceId: number) => Observable.of([]),
            getWard: (wardId: number, districtId: number) => Observable.of([]),
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
