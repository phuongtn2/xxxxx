import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../../core/services/http.service';
import { NurseryReportService } from './nursery-report.service';

const httpServiceStub = {
};

describe('NurseryReportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        NurseryReportService
      ]
    });
  });

  it('should ...', inject([NurseryReportService], (service: NurseryReportService) => {
    expect(service).toBeTruthy();
  }));
});
