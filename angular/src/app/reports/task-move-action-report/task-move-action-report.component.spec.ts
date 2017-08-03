import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule, MdNativeDateModule } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Observable } from 'rxjs/Observable';

import { TaskMoveActionReportComponent } from './task-move-action-report.component';
import { UserService } from '../../users/user.service';
import { TaskMoveActionReportService } from './task-move-action-report.service';

describe('TaskMoveActionReportComponent', () => {
  let component: TaskMoveActionReportComponent;
  let fixture: ComponentFixture<TaskMoveActionReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TaskMoveActionReportComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        MdNativeDateModule,
        NoopAnimationsModule,
        ReactiveFormsModule
      ]
    })
    .overrideComponent(TaskMoveActionReportComponent, {
      set: {
        providers: [
          DatePipe,
          { provide: UserService, useValue: {
            getResponsibilityEmployees: () => Observable.of([])
          } },
          { provide: TaskMoveActionReportService, useValue: {} }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskMoveActionReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
