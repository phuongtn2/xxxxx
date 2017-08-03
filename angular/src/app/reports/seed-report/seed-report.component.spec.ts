import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { SeedReportComponent } from './seed-report.component';
import { MaterialService } from '../../materials/material.service';
import { SeedReportService } from './seed-report.service';

const materialServiceStub = {
  getSeeds: () => Observable.of([])
};

const seedReportServiceStub = {
  getData: () => Observable.of([])
};

describe('SeedReportComponent', () => {
  let component: SeedReportComponent;
  let fixture: ComponentFixture<SeedReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeedReportComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule
      ]
    })
    .overrideComponent(SeedReportComponent, {
      set: {
        providers: [
          { provide: MaterialService, useValue: materialServiceStub },
          { provide: SeedReportService, useValue: seedReportServiceStub }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeedReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
