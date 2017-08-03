import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../../core/services/http.service';
import { SeedReportService } from './seed-report.service';

const httpServiceStub = {
};

describe('SeedReportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        SeedReportService
      ]
    });
  });

  it('should ...', inject([SeedReportService], (service: SeedReportService) => {
    expect(service).toBeTruthy();
  }));
});
