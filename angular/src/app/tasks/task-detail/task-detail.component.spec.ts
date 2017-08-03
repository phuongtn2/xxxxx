import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule, MdNativeDateModule } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { Observable } from 'rxjs/Observable';

import { TaskDetailComponent } from './task-detail.component';
import { LocationModule } from '../../location/location.module';
import { NotificationService } from '../../core/services/notification.service';
import { UserService } from '../../users/user.service';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { LocationService } from '../../core/services/location.service';
import { TaskDetailService } from './task-detail.service';
import { FieldService } from '../../fields/field.service';
import { NurseryService } from '../../nurseries/nursery.service';

describe('TaskDetailComponent', () => {
  let component: TaskDetailComponent;
  let fixture: ComponentFixture<TaskDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TaskDetailComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        MdNativeDateModule,
        NoopAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        LocationModule
      ],
      providers: [
        DatePipe,
        NotificationService,
        { provide: FieldService, useValue: {} },
        { provide: NurseryService, useValue: {} }
      ]
    })
    .overrideComponent(TaskDetailComponent, {
      set: {
        providers: [
          { provide: UserService, useValue: {
            get: (userId: number) => Observable.throw(new Error('Page not found.'))
          } },
          { provide: CropSessionService, useValue: {
            getByResponsibilityEmployee: (employeeId: number) => Observable.of([])
          } },
          { provide: TaskDetailService, useValue: {} },
          { provide: LocationService, useValue: {
            getProvinces: () => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskDetailComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    try {
      fixture.detectChanges();
    } catch (e) {
      expect(e.message).toEqual('Page not found.');
    }
  });
});
