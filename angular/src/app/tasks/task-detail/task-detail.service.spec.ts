import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../../core/services/http.service';
import { TaskDetailService } from './task-detail.service';

const httpServiceStub = {
};

describe('TaskDetailService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        TaskDetailService
      ]
    });
  });

  it('should ...', inject([TaskDetailService], (service: TaskDetailService) => {
    expect(service).toBeTruthy();
  }));
});
