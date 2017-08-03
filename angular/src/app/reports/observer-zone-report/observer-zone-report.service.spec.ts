import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../../core/services/http.service';
import { ObserverZoneReportService } from './observer-zone-report.service';

const httpServiceStub = {
};

describe('ObserverZoneReportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        ObserverZoneReportService
      ]
    });
  });

  it('should ...', inject([ObserverZoneReportService], (service: ObserverZoneReportService) => {
    expect(service).toBeTruthy();
  }));
});
