import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../../core/services/http.service';
import { ExpectedOutputReportService } from './expected-output-report.service';

const httpServiceStub = {
};

describe('ExpectedOutputReportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        ExpectedOutputReportService
      ]
    });
  });

  it('should ...', inject([ExpectedOutputReportService], (service: ExpectedOutputReportService) => {
    expect(service).toBeTruthy();
  }));
});
