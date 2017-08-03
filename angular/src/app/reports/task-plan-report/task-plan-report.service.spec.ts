import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../../core/services/http.service';
import { TaskPlanReportService } from './task-plan-report.service';

const httpServiceStub = {
};

describe('TaskPlanReportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        TaskPlanReportService
      ]
    });
  });

  it('should ...', inject([TaskPlanReportService], (service: TaskPlanReportService) => {
    expect(service).toBeTruthy();
  }));
});
