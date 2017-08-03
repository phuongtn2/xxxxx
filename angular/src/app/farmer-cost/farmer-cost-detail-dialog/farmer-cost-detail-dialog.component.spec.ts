import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule, MdDialogRef } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Observable } from 'rxjs/Observable';

import { FarmerCostDetailDialogComponent } from './farmer-cost-detail-dialog.component';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { AuthenticationService } from '../../core/services/authentication.service';
import { NotificationService } from '../../core/services/notification.service';
import { MaterialService } from '../../materials/material.service';
import { FarmerCostService } from '../farmer-cost.service';
import { User } from '../../core/models/user';

describe('FarmerCostDetailDialogComponent', () => {
  let component: FarmerCostDetailDialogComponent;
  let fixture: ComponentFixture<FarmerCostDetailDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FarmerCostDetailDialogComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        NoopAnimationsModule,
        ReactiveFormsModule
      ],
      providers: [
        { provide: MdDialogRef, useValue: {} },
        { provide: AuthenticationService, useValue: {
          currentUser: new User()
        } },
        { provide: NotificationService, useValue: {} },
        { provide: FarmerCostService, useValue: {} }
      ]
    })
    .overrideComponent(FarmerCostDetailDialogComponent, {
      set: {
        providers: [
          { provide: MaterialService, useValue: {
            getAll: () => Observable.of([])
          } },
          { provide: CropSessionService, useValue: {
            getByResponsibilityEmployee: (employeeId: number) => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FarmerCostDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
