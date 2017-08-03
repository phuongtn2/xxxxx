import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../../core/services/http.service';
import { SaleService } from './sale.service';

const httpServiceStub = {
};

describe('SaleService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        SaleService
      ]
    });
  });

  it('should ...', inject([SaleService], (service: SaleService) => {
    expect(service).toBeTruthy();
  }));
});
