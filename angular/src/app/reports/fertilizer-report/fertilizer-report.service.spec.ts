import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../../core/services/http.service';
import { FertilizerReportService } from './fertilizer-report.service';

const httpServiceStub = {
};

describe('FertilizerReportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        FertilizerReportService
      ]
    });
  });

  it('should ...', inject([FertilizerReportService], (service: FertilizerReportService) => {
    expect(service).toBeTruthy();
  }));
});
