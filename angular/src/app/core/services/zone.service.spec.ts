import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from './http.service';
import { ZoneService } from './zone.service';

const httpServiceStub = {
};

describe('ZoneService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        ZoneService
      ]
    });
  });

  it('should ...', inject([ZoneService], (service: ZoneService) => {
    expect(service).toBeTruthy();
  }));
});
