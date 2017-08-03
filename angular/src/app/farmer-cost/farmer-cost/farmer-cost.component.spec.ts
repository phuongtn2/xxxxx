import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MdDataTableModule } from '@dtrthi/md-data-table';
import { Observable } from 'rxjs/Observable';

import { FarmerCostComponent } from './farmer-cost.component';
import { AuthenticationService } from '../../core/services/authentication.service';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { FarmerCostService } from '../farmer-cost.service';
import { User } from '../../core/models/user';

describe('FarmerCostComponent', () => {
  let component: FarmerCostComponent;
  let fixture: ComponentFixture<FarmerCostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FarmerCostComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        MdDataTableModule,
        NoopAnimationsModule
      ],
      providers: [
        { provide: AuthenticationService, useValue: {
          currentUser: new User()
        } },
        { provide: FarmerCostService, useValue: {
          getAll: () => Observable.of([])
        } }
      ]
    })
    .overrideComponent(FarmerCostComponent, {
      set: {
        providers: [
          { provide: CropSessionService, useValue: {
            getAll: () => Observable.of([]),
            getByResponsibilityEmployee: (employeeId: number) => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FarmerCostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
