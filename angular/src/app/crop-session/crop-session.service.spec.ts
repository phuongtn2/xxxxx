import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../core/services/http.service';
import { CropSessionService } from './crop-session.service';

const httpServiceStub = {
};

describe('CropSessionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        CropSessionService
      ]
    });
  });

  it('should ...', inject([CropSessionService], (service: CropSessionService) => {
    expect(service).toBeTruthy();
  }));
});
