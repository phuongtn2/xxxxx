import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from './http.service';
import { LocationService } from './location.service';

const httpServiceStub = {
};

describe('LocationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        LocationService
      ]
    });
  });

  it('should ...', inject([LocationService], (service: LocationService) => {
    expect(service).toBeTruthy();
  }));
});
