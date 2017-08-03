import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../../core/services/http.service';
import { FarmerCostReportService } from './farmer-cost-report.service';

const httpServiceStub = {
};

describe('FarmerCostReportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        FarmerCostReportService
      ]
    });
  });

  it('should ...', inject([FarmerCostReportService], (service: FarmerCostReportService) => {
    expect(service).toBeTruthy();
  }));
});
