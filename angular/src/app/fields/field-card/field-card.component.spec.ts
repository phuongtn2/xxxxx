import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';

import { FieldCardComponent } from './field-card.component';
import { FieldService } from '../field.service';
import { MaterialService } from '../../materials/material.service';
import { LocationService } from '../../core/services/location.service';

describe('FieldCardComponent', () => {
  let component: FieldCardComponent;
  let fixture: ComponentFixture<FieldCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FieldCardComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        RouterTestingModule
      ],
      providers: [
        { provide: FieldService, useValue: {} }
      ]
    })
    .overrideComponent(FieldCardComponent, {
      set: {
        providers: [
          { provide: MaterialService, useValue: {} },
          { provide: LocationService, useValue: {} }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FieldCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
