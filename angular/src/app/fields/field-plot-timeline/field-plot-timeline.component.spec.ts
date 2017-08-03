import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';
import { Observable } from 'rxjs/Observable';

import { FieldPlotTimelineComponent } from './field-plot-timeline.component';
import { AuthenticationService } from '../../core/services/authentication.service';
import { TimelineModule } from '../../timeline/timeline.module';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { FieldService } from '../field.service';
import { Registration } from '../../crop-session/registration';
import { Field } from '../field';

describe('FieldPlotTimelineComponent', () => {
  let component: FieldPlotTimelineComponent;
  let fixture: ComponentFixture<FieldPlotTimelineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FieldPlotTimelineComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        TimelineModule,
        RouterTestingModule
      ],
      providers: [
        { provide: AuthenticationService, useValue: {} },
        { provide: FieldService, useValue: {
          getField: (registrationId: any, fieldId: any) => Observable.of(new Field())
        } }
      ]
    })
    .overrideComponent(FieldPlotTimelineComponent, {
      set: {
        providers: [
          { provide: CropSessionService, useValue: {
            getRegistrationByFarmerCode: (farmerCode: string) => Observable.of(new Registration())
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FieldPlotTimelineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
