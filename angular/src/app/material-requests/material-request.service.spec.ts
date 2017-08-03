import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../core/services/http.service';
import { MaterialRequestService } from './material-request.service';

const httpServiceStub = {
};

describe('MaterialRequestService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        MaterialRequestService
      ]
    });
  });

  it('should ...', inject([MaterialRequestService], (service: MaterialRequestService) => {
    expect(service).toBeTruthy();
  }));
});
