import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormBuilder } from '@angular/forms';
import {
  MdButtonModule,
  MdCardModule,
  MdCheckboxModule,
  MdIconModule,
  MdListModule,
  MdProgressSpinnerModule,
  MdTooltipModule
} from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { TechnicianZoneListComponent } from './technician-zone-list.component';
import { LocationService } from '../../../core/services/location.service';
import { ZoneService } from '../../../core/services/zone.service';
import { User } from '../../../core/models/user';
import { TechnicianZoneService } from '../technician-zone.service';

describe('TechnicianZoneListComponent', () => {
  let component: TechnicianZoneListComponent;
  let fixture: ComponentFixture<TechnicianZoneListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TechnicianZoneListComponent ],
      imports: [
        FlexLayoutModule,
        MdButtonModule,
        MdCardModule,
        MdCheckboxModule,
        MdIconModule,
        MdListModule,
        MdProgressSpinnerModule,
        MdTooltipModule
      ],
      providers: [
        FormBuilder,
        { provide: LocationService, useValue: {
          getProvinces: () => Observable.of([]),
          getAllDistricts: () => Observable.of([])
        } }
      ]
    })
    .overrideComponent(
      TechnicianZoneListComponent, {
        set: {
          providers: [
            { provide: ZoneService, useValue: {
              getZones: () => Observable.of([])
            } },
            { provide: TechnicianZoneService, useValue: {
              getByTechnicianId: () => Observable.of([]),
              sync: () => Observable.of([])
            } }
          ]
        }
      }
    )
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TechnicianZoneListComponent);
    component = fixture.componentInstance;
  });

  it('should be created', () => {
    component.user = new User();
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it(`should throw an error with message 'Must pass a user instance.'`, () => {
    try {
      fixture.detectChanges();
    } catch (e) {
      expect(e.message).toEqual('Must pass a user instance.');
    }
  });
});
