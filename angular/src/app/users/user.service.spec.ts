import { TestBed, inject } from '@angular/core/testing';

import { AuthenticationService } from '../core/services/authentication.service';
import { HttpService } from '../core/services/http.service';
import { UserService } from './user.service';

const authServiceStub = {
};

const httpServiceStub = {
};

describe('UserService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useValue: httpServiceStub },
        { provide: AuthenticationService, useValue: authServiceStub },
        UserService
      ]
    });
  });

  it('should ...', inject([UserService], (service: UserService) => {
    expect(service).toBeTruthy();
  }));
});
