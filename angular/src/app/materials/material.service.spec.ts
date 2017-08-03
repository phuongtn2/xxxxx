import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../core/services/http.service';
import { MaterialService } from './material.service';

describe('MaterialService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: {} },
        MaterialService
      ]
    });
  });

  it('should ...', inject([MaterialService], (service: MaterialService) => {
    expect(service).toBeTruthy();
  }));
});
