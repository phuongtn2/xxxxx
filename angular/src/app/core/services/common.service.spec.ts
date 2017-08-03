import { TestBed, inject } from '@angular/core/testing';

import { CommonService } from './common.service';
import { HttpService } from './http.service';

const httpServiceStub = {
};

describe('CommonService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        CommonService
      ]
    });
  });

  it('should ...', inject([CommonService], (service: CommonService) => {
    expect(service).toBeTruthy();
  }));
});
