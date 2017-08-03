import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { HttpService } from '../core/services/http.service';
import { Field } from './field';

@Injectable()
export class FieldService {

  constructor(private http: HttpService) { }

  getField(registrationId: any, fieldId: any): Observable<Field> {
    registrationId = encodeURIComponent(registrationId);
    fieldId = encodeURIComponent(fieldId);
    const requestUrl = `/api/cultivation/${registrationId}/get/${fieldId}`;
    return this.http.get(requestUrl)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        const r = response.json();
        return Array.isArray(r.data) && Field.assign(r.data[0]) || null;
      });
  }

  register(registrationId, field: Field) {
    registrationId = encodeURIComponent(registrationId);
    const requestUrl = `/api/cultivation/${registrationId}/add`;
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});
    const body = new URLSearchParams();
    body.append('address', field.address);
    body.append('provinceId', field.provinceId.toString());
    body.append('districtId', field.districtId.toString());
    body.append('wardId', field.wardId.toString());
    body.append('seedId', field.seedId.toString());
    return this.http.post(requestUrl, body.toString(), options)
      .map(
        (response) => {
          const r = response.json();
          if (r.code === 'request_completed' && Array.isArray(r.data)) {
            return Field.assign(r.data[0]);
          }
          return null;
        }
      );
  }

  update(registrationId, field: Field) {
    registrationId = encodeURIComponent(registrationId);
    const fieldId = encodeURIComponent(field.id.toString());
    const requestUrl = `/api/cultivation/${registrationId}/${fieldId}/update`;
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});
    const body = new URLSearchParams();
    body.append('id', field.id.toString());
    body.append('address', field.address);
    body.append('provinceId', field.provinceId.toString());
    body.append('districtId', field.districtId.toString());
    body.append('wardId', field.wardId.toString());
    body.append('seedId', field.seedId.toString());
    return this.http.post(requestUrl, body.toString(), options)
      .map(
        (response) => {
          const r = response.json();
          if (r.code === 'request_completed' && Array.isArray(r.data)) {
            return Field.assign(r.data[0]);
          }
          return null;
        }
      );
  }
}
