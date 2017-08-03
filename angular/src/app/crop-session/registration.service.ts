import { Injectable } from '@angular/core';
import { Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';

import { Registration } from './registration';
import { HttpService } from '../core/services/http.service';

@Injectable()
export class CropRegisterService {

  constructor(
    private http: HttpService
  ) { }

  save(registration: Registration) {
    if (registration.id) {
      return this.update(registration);
    }
    return this.register(registration);
  }

  register(registration: Registration) {
    const requestUrl = '/api/crop-session/farmer/register';
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});

    const body = new URLSearchParams();
    // TODO: may remove
    body.append('cropId', '1');
    body.append('exp', registration.exp.toString());
    body.append('farmerId', registration.farmerId.toString());
    body.append('householdMembers', registration.householdMembers.toString());
    body.append('labors', registration.labors.toString());
    body.append('memo', registration.memo);
    body.append('registrationAcreage', registration.registrationAcreage.toString());
    body.append('responsibilityEmployeeId', registration.responsibilityEmployeeId.toString());
    // TODO will remove
    body.append('zoneId', registration.zoneId);
    return this.http.post(requestUrl, body.toString(), options)
      .map(
        (response: Response) => {
          const r = response.json();
          if (r.code === 'request_completed') {
            if (Array.isArray(r.data) && r.data[0]) {
              return Registration.assign(r.data[0]);
            }
          }
          return null;
        }
      );
  }

  update(registration: Registration) {
    const id = encodeURIComponent(registration.id.toString());
    const requestUrl = `/api/crop-session/farmer/update/${id}`;
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});

    const body = new URLSearchParams();
    // TODO: may remove
    body.append('cropId', '1');
    body.append('exp', registration.exp.toString());
    body.append('farmerId', registration.farmerId.toString());
    body.append('householdMembers', registration.householdMembers.toString());
    body.append('labors', registration.labors.toString());
    body.append('memo', registration.memo);
    body.append('registrationAcreage', registration.registrationAcreage.toString());
    body.append('responsibilityEmployeeId', registration.responsibilityEmployeeId.toString());
    // TODO will remove
    body.append('zoneId', registration.zoneId);
    body.append('isCancel', registration.isCancel.toString());
    return this.http.post(requestUrl, body.toString(), options)
      .map(
        (response: Response) => {
          const r = response.json();
          if (r.code === 'request_completed') {
            if (Array.isArray(r.data) && r.data[0]) {
              return Registration.assign(r.data[0]);
            }
          }
          return null;
        }
      );
  }

  cancel(registration: Registration) {
    registration.isCancel = true;
    return this.update(registration);
  }

  registerAgain(registration: Registration) {
    registration.isCancel = false;
    return this.update(registration);
  }
}
