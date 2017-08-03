import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';
import { MdDataTableModule } from '@dtrthi/md-data-table';
import { Observable } from 'rxjs/Observable';

import { PlotActionDetailComponent } from './plot-action-detail.component';
import { LocationModule } from '../../location/location.module';
import { AuthenticationService } from '../../core/services/authentication.service';
import { CommonService } from '../../core/services/common.service';
import { PestService } from '../../core/services/pest.service';
import { MaterialService } from '../../materials/material.service';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { FieldService } from '../field.service';
import { Registration } from '../../crop-session/registration';
import { Field } from '../field';

describe('PlotActionDetailComponent', () => {
  let component: PlotActionDetailComponent;
  let fixture: ComponentFixture<PlotActionDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlotActionDetailComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        MdDataTableModule,
        RouterTestingModule,
        LocationModule
      ],
      providers: [
        { provide: AuthenticationService, useValue: {} },
        { provide: FieldService, useValue: {
          getField: (registrationId: any, fieldId: any) => Observable.of(new Field())
        } }
      ]
    })
    .overrideComponent(PlotActionDetailComponent, {
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
    fixture = TestBed.createComponent(PlotActionDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
