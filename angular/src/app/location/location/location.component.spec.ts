import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MdSelectModule } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { LocationComponent } from './location.component';
import { LocationService } from '../../core/services/location.service';

describe('LocationComponent', () => {
  let component: LocationComponent;
  let fixture: ComponentFixture<LocationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LocationComponent ],
      imports: [
        FlexLayoutModule,
        MdSelectModule,
        ReactiveFormsModule
      ],
      providers: [
        { provide: LocationService, useValue: {
          getProvinces: () => Observable.of([])
        } }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
