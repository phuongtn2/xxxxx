import { TestBed, inject } from '@angular/core/testing';

import { TechnicianZoneService } from './technician-zone.service';
import { HttpService } from '../../core/services/http.service';

describe('TechnicianZoneService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: {} },
        TechnicianZoneService
      ]
    });
  });

  it('should be created', inject([TechnicianZoneService], (service: TechnicianZoneService) => {
    expect(service).toBeTruthy();
  }));
});
