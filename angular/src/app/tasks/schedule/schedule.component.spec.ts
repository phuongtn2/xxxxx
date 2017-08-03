import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { Observable } from 'rxjs/Observable';

import { ScheduleComponent } from './schedule.component';
import { AuthenticationService } from '../../core/services/authentication.service';
import { AppCalendarModule } from '../../app-calendar/app-calendar.module';
import { TaskListComponent } from '../task-list/task-list.component';
import { TaskService } from '../task.service';

describe('ScheduleComponent', () => {
  let component: ScheduleComponent;
  let fixture: ComponentFixture<ScheduleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        ScheduleComponent,
        TaskListComponent
      ],
      imports: [
        FlexLayoutModule,
        FormsModule,
        MaterialModule,
        NoopAnimationsModule,
        RouterTestingModule,
        AppCalendarModule
      ],
      providers: [
        { provide: AuthenticationService, useValue: {} }
      ]
    })
    .overrideComponent(ScheduleComponent, {
      set: {
        providers: [
          { provide: TaskService, useValue: {
            getSchedule: (assigneeId: number) => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
