import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';
import { MdDataTableModule } from '@dtrthi/md-data-table';
import { Observable } from 'rxjs/Observable';

import { PlotDetailComponent } from './plot-detail.component';
import { FieldService } from '../field.service';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { LocationService } from '../../core/services/location.service';
import { Field } from '../field';

describe('PlotDetailComponent', () => {
  let component: PlotDetailComponent;
  let fixture: ComponentFixture<PlotDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlotDetailComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        MdDataTableModule,
        RouterTestingModule
      ],
      providers: [
        { provide: FieldService, useValue: {
          getField: (registrationId: any, fieldId: any) => Observable.of(new Field())
        } }
      ]
    })
    .overrideComponent(PlotDetailComponent, {
      set: {
        providers: [
          { provide: CropSessionService, useValue: {
            getRegistrationByFarmerCode: (farmerCode: string) => Observable.of([])
          } },
          { provide: LocationService, useValue: {} }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlotDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
