import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';
import { MdDataTableModule } from '@dtrthi/md-data-table';
import { Observable } from 'rxjs/Observable';

import { MaterialRequestDetailComponent } from './material-request-detail.component';
import { AuthenticationService } from '../../core/services/authentication.service';
import { NotificationService } from '../../core/services/notification.service';
import { MaterialService } from '../../materials/material.service';
import { MaterialRequestService } from '../material-request.service';
import { MaterialRequest } from '../material-request';

describe('MaterialRequestDetailComponent', () => {
  let component: MaterialRequestDetailComponent;
  let fixture: ComponentFixture<MaterialRequestDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MaterialRequestDetailComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        MdDataTableModule,
        RouterTestingModule
      ],
      providers: [
        NotificationService,
        { provide: AuthenticationService, useValue: {} }
      ]
    })
    .overrideComponent(MaterialRequestDetailComponent, {
      set: {
        providers: [
          { provide: MaterialService, useValue: {
            getAll: () => Observable.of([])
          } },
          { provide: MaterialRequestService, useValue: {
            get: (materialRequestId: number) => Observable.of(new MaterialRequest())
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MaterialRequestDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
