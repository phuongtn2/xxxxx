import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';

import { NurseryCardComponent } from './nursery-card.component';
import { LocationService } from '../../core/services/location.service';
import { NurseryService } from '../nursery.service';
import { MaterialService } from '../../materials/material.service';

describe('NurseryCardComponent', () => {
  let component: NurseryCardComponent;
  let fixture: ComponentFixture<NurseryCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NurseryCardComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        RouterTestingModule
      ],
      providers: [
        { provide: LocationService, useValue: {} },
        { provide: NurseryService, useValue: {} }
      ]
    })
    .overrideComponent(NurseryCardComponent, {
      set: {
        providers: [
          { provide: MaterialService, useValue: {} }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NurseryCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
