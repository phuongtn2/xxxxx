import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';
import { Observable } from 'rxjs/Observable';

import { TimelineModule } from '../../timeline/timeline.module';
import { AuthenticationService } from '../../core/services/authentication.service';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { NurseryService } from '../nursery.service';
import { NurseryTimelineComponent } from './nursery-timeline.component';
import { Registration } from '../../crop-session/registration';

const cropSessionServiceStub = {
  getRegistrationByFarmerCode: (farmerCode: string) => Observable.of(new Registration())
};
const nurseryServiceStub = {
  getNursery: (registrationId, nurseryId) => Observable.of(null)
};

describe('NurseryTimelineComponent', () => {
  let component: NurseryTimelineComponent;
  let fixture: ComponentFixture<NurseryTimelineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NurseryTimelineComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        TimelineModule,
        RouterTestingModule
      ],
      providers: [
        { provide: AuthenticationService, useValue: {} },
        { provide: NurseryService, useValue: nurseryServiceStub }
      ]
    })
    .overrideComponent(NurseryTimelineComponent, {
      set: {
        providers: [
          { provide: CropSessionService, useValue: cropSessionServiceStub }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NurseryTimelineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
