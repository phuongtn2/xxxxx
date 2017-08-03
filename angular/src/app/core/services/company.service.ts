import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { HttpService } from './http.service';
import { Company } from '../models/company';

@Injectable()
export class CompanyService {

  constructor(
    private http: HttpService
  ) { }

  getCompanies(): Observable<Array<Company>> {
    return this.http.get('/api/company/get')
      .map(
        (response) => {
          const r = response.json();
          if (Array.isArray(r)) {
            return r.map((value) => Company.assign(value));
          }
          return [];
        }
      );
  }
}
