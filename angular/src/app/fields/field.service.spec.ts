import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../core/services/http.service';
import { FieldService } from './field.service';

const httpServiceStub = {
};

describe('FieldService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        FieldService
      ]
    });
  });

  it('should ...', inject([FieldService], (service: FieldService) => {
    expect(service).toBeTruthy();
  }));
});
