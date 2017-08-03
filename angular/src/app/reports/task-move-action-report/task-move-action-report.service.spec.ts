import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../../core/services/http.service';
import { TaskMoveActionReportService } from './task-move-action-report.service';

const httpServiceStub = {
};

describe('TaskMoveActionReportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        TaskMoveActionReportService
      ]
    });
  });

  it('should ...', inject([TaskMoveActionReportService], (service: TaskMoveActionReportService) => {
    expect(service).toBeTruthy();
  }));
});
