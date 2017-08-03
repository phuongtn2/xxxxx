import { TestBed, inject } from '@angular/core/testing';
import { Http } from '@angular/http';

import { AuthenticationService } from './authentication.service';
import { HttpService } from './http.service';

const httpStub = {};

describe('HttpService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: Http, useValue: httpStub },
        { provide: AuthenticationService, useValue: {} },
        HttpService
      ]
    });
  });

  it('should ...', inject([HttpService], (service: HttpService) => {
    expect(service).toBeTruthy();
  }));
});
