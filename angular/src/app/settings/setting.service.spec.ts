import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from '../core/services/http.service';
import { SettingService } from './setting.service';
import { AuthenticationService } from '../core/services/authentication.service';

const authServiceStub = {
};

const httpServiceStub = {
};

describe('SettingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        { provide: AuthenticationService, useValue: authServiceStub },
        SettingService
      ]
    });
  });

  it('should ...', inject([SettingService], (service: SettingService) => {
    expect(service).toBeTruthy();
  }));
});
