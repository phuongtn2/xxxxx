import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from './http.service';
import { ObserverService } from './observer.service';

const httpServiceStub = {
};

describe('ObserverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        ObserverService
      ]
    });
  });

  it('should ...', inject([ObserverService], (service: ObserverService) => {
    expect(service).toBeTruthy();
  }));
});
