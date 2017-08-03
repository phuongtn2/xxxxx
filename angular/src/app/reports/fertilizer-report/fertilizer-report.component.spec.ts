import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { FertilizerReportComponent } from './fertilizer-report.component';
import { MaterialService } from '../../materials/material.service';
import { FertilizerReportService } from './fertilizer-report.service';

describe('FertilizerReportComponent', () => {
  let component: FertilizerReportComponent;
  let fixture: ComponentFixture<FertilizerReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FertilizerReportComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule
      ]
    })
    .overrideComponent(FertilizerReportComponent, {
      set: {
        providers: [
          { provide: MaterialService, useValue: {
            getAll: () => Observable.of([])
          } },
          { provide: FertilizerReportService, useValue: {
            getData: () => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FertilizerReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
