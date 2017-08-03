import { TestBed, inject } from '@angular/core/testing';

import { FarmerService } from './farmer.service';
import { HttpService } from './http.service';

const httpServiceStub = {
};

describe('FarmerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        FarmerService
      ]
    });
  });

  it('should ...', inject([FarmerService], (service: FarmerService) => {
    expect(service).toBeTruthy();
  }));
});
