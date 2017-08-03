import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../../core/services/http.service';
import { FarmerReportService } from './farmer-report.service';

const httpServiceStub = {
};

describe('FarmerReportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        FarmerReportService
      ]
    });
  });

  it('should ...', inject([FarmerReportService], (service: FarmerReportService) => {
    expect(service).toBeTruthy();
  }));
});
