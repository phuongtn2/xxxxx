import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../../core/services/http.service';
import { ActualAcreageReportService } from './actual-acreage-report.service';

const httpServiceStub = {
};

describe('ActualAcreageReportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        ActualAcreageReportService
      ]
    });
  });

  it('should ...', inject([ActualAcreageReportService], (service: ActualAcreageReportService) => {
    expect(service).toBeTruthy();
  }));
});
