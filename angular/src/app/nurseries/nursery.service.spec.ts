import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../core/services/http.service';
import { NurseryService } from './nursery.service';

const httpServiceStub = {
};

describe('NurseryService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        NurseryService
      ]
    });
  });

  it('should ...', inject([NurseryService], (service: NurseryService) => {
    expect(service).toBeTruthy();
  }));
});
