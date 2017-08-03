import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../core/services/http.service';
import { FarmerCostService } from './farmer-cost.service';

const httpServiceStub = {
};

describe('FarmerCostService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        FarmerCostService
      ]
    });
  });

  it('should ...', inject([FarmerCostService], (service: FarmerCostService) => {
    expect(service).toBeTruthy();
  }));
});
