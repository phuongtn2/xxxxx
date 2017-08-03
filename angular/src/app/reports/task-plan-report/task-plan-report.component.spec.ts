import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Observable } from 'rxjs/Observable';

import { TaskPlanReportComponent } from './task-plan-report.component';
import { UserService } from '../../users/user.service';
import { TaskPlanReportService } from './task-plan-report.service';

describe('TaskPlanReportComponent', () => {
  let component: TaskPlanReportComponent;
  let fixture: ComponentFixture<TaskPlanReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TaskPlanReportComponent ],
      imports: [
        // TODO: check this
        NoopAnimationsModule,
        FlexLayoutModule,
        MaterialModule,
        ReactiveFormsModule
      ]
    })
    .overrideComponent(TaskPlanReportComponent, {
      set: {
        providers: [
          { provide: UserService, useValue: {
            getResponsibilityEmployees: () => Observable.of([])
          } },
          { provide: TaskPlanReportService, useValue: {} }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskPlanReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
