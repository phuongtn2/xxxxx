import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from './http.service';
import { CompanyService } from './company.service';

const httpServiceStub = {
};

describe('CompanyService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        CompanyService
      ]
    });
  });

  it('should ...', inject([CompanyService], (service: CompanyService) => {
    expect(service).toBeTruthy();
  }));
});
