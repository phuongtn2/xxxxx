import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';
import { MdDataTableModule } from '@dtrthi/md-data-table';
import { Observable } from 'rxjs/Observable';

import { NurseryActionDetailComponent } from './nursery-action-detail.component';
import { LocationModule } from '../../location/location.module';
import { AuthenticationService } from '../../core/services/authentication.service';
import { CommonService } from '../../core/services/common.service';
import { PestService } from '../../core/services/pest.service';
import { MaterialService } from '../../materials/material.service';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { NurseryService } from '../nursery.service';
import { Registration } from '../../crop-session/registration';
import { Nursery } from '../nursery';

describe('NurseryActionDetailComponent', () => {
  let component: NurseryActionDetailComponent;
  let fixture: ComponentFixture<NurseryActionDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NurseryActionDetailComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        MdDataTableModule,
        RouterTestingModule,
        LocationModule
      ],
      providers: [
        { provide: AuthenticationService, useValue: {} },
        { provide: NurseryService, useValue: {
          getNursery: (registrationId, nurseryId) => Observable.of(new Nursery())
        } }
      ]
    })
    .overrideComponent(NurseryActionDetailComponent, {
      set: {
        providers: [
          { provide: CommonService, useValue: {} },
          { provide: PestService, useValue: {} },
          { provide: MaterialService, useValue: {} },
          { provide: CropSessionService, useValue: {
            getRegistrationByFarmerCode: (farmerCode: string) => Observable.of(new Registration())
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NurseryActionDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
