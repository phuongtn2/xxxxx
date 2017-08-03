import { TestBed, inject } from '@angular/core/testing';

import { CommonService } from './common.service';
import { PestService } from './pest.service';

const commonServiceStub = {
};

describe('PestService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: CommonService, useValue: commonServiceStub },
        PestService]
    });
  });

  it('should ...', inject([PestService], (service: PestService) => {
    expect(service).toBeTruthy();
  }));
});
