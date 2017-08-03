import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { HttpService } from '../core/services/http.service';
import { Nursery } from './nursery';

@Injectable()
export class NurseryService {

  constructor(
    private http: HttpService
  ) { }

  getNursery(registrationId, nurseryId): Observable<Nursery> {
    registrationId = encodeURIComponent(registrationId);
    nurseryId = encodeURIComponent(nurseryId);
    const requestUrl = `/api/crop-session/nursery/${registrationId}/get/${nurseryId}`;
    return this.http.get(requestUrl)
      .map(
        (response: Response) => {
          const r = response.json();
          return Array.isArray(r.data) && Nursery.assign(r.data[0]) || null;
        }
      );
  }

  save(registrationId, nursery: Nursery) {
    if (nursery.id) {
      return this.update(registrationId, nursery);
    }
    return this.register(registrationId, nursery);
  }

  register(registrationId, nursery: Nursery) {
    registrationId = encodeURIComponent(registrationId);
    const requestUrl = `/api/crop-session/nursery/${registrationId}/add`;
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});
    const body = new URLSearchParams();
    body.append('address', nursery.address);
    body.append('provinceId', nursery.provinceId.toString());
    body.append('districtId', nursery.districtId.toString());
    body.append('wardId', nursery.wardId.toString());
    body.append('seedId', nursery.seedId.toString());
    return this.http.post(requestUrl, body.toString(), options)
      .map(
        (response) => {
          const r = response.json();
          if (r.code === 'request_completed' && Array.isArray(r.data)) {
            return Nursery.assign(r.data[0]);
          }
          return null;
        }
      );
  }

  update(registrationId, nursery: Nursery) {
    registrationId = encodeURIComponent(registrationId);
    const requestUrl = `/api/crop-session/nursery/${registrationId}/update`;
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});
    const body = new URLSearchParams();
    body.append('id', nursery.id.toString());
    body.append('address', nursery.address);
    body.append('provinceId', nursery.provinceId.toString());
    body.append('districtId', nursery.districtId.toString());
    body.append('wardId', nursery.wardId.toString());
    body.append('seedId', nursery.seedId.toString());
    return this.http.post(requestUrl, body.toString(), options)
      .map(
        (response) => {
          const r = response.json();
          if (r.code === 'request_completed' && Array.isArray(r.data)) {
            return Nursery.assign(r.data[0]);
          }
          return null;
        }
      );
  }
}
