import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../core/services/http.service';
import { CropRegisterService } from './registration.service';

const httpServiceStub = {
};

describe('CropRegisterService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        CropRegisterService
      ]
    });
  });

  it('should ...', inject([CropRegisterService], (service: CropRegisterService) => {
    expect(service).toBeTruthy();
  }));
});
