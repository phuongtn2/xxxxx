import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../core/services/http.service';
import { CompanyMaterialService } from './company-material.service';

const httpServiceStub = {
};

describe('CompanyMaterialService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        CompanyMaterialService
      ]
    });
  });

  it('should ...', inject([CompanyMaterialService], (service: CompanyMaterialService) => {
    expect(service).toBeTruthy();
  }));
});
