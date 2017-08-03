import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../core/services/http.service';
import { TaskService } from './task.service';

const httpServiceStub = {
};

describe('TaskService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        TaskService
      ]
    });
  });

  it('should ...', inject([TaskService], (service: TaskService) => {
    expect(service).toBeTruthy();
  }));
});
