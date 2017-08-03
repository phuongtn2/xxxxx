import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { HttpService } from '../../core/services/http.service';
import { SaleRegister } from '../sale-register';

@Injectable()
export class SaleService {

  constructor(
    private http: HttpService
  ) { }

  getList(registrationId: number|string): Observable<SaleRegister[]> {
    registrationId = encodeURIComponent(registrationId.toString());
    const requestUrl = `/api/sale/get/${registrationId}`;
    return this.http.get(requestUrl)
      .map(
        (response: Response) => {
          const r = response.json();
          if (Array.isArray(r)) {
            return r.map(value => SaleRegister.assign(value));
          }
          return [];
        }
      );
  }
}
