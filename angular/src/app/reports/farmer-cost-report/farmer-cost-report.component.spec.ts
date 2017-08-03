import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { FarmerCostReportComponent } from './farmer-cost-report.component';
import { FromCharCodePipe } from '../../core/pipes/from-char-code.pipe';
import { FarmerCostReportService } from './farmer-cost-report.service';

describe('FarmerCostReportComponent', () => {
  let component: FarmerCostReportComponent;
  let fixture: ComponentFixture<FarmerCostReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        FarmerCostReportComponent,
        FromCharCodePipe
      ],
      imports: [
        FlexLayoutModule,
        MaterialModule
      ]
    })
    .overrideComponent(FarmerCostReportComponent, {
      set: {
        providers: [
          { provide: FarmerCostReportService, useValue: {
            getData: () => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FarmerCostReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
