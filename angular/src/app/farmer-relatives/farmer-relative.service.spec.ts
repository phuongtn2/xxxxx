import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../core/services/http.service';
import { FarmerRelativeService } from './farmer-relative.service';

const httpServiceStub = {
};

describe('FarmerRelativeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        FarmerRelativeService
      ]
    });
  });

  it('should ...', inject([FarmerRelativeService], (service: FarmerRelativeService) => {
    expect(service).toBeTruthy();
  }));
});
