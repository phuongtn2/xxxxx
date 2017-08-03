import { Injectable } from '@angular/core';
import { URLSearchParams, RequestOptions, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { HttpService } from './http.service';
import { Farmer } from '../models/farmer';
import { Pagination } from '../models/pagination';

@Injectable()
export class FarmerService {

  constructor(
    private http: HttpService
  ) { }

  getAll(): Observable<Farmer[]> {
    const requestUrl = '/api/farmer/sync';
    return this.http.get(requestUrl)
      .map(
        (response: Response) => {
          const json = response.json();
          if (Array.isArray(json)) {
            return json.map(value => Farmer.assign(value));
          }
          return [];
        }
      );
  }

  getUnregisteredFarmers(): Observable<Farmer[]> {
    return this.getAll()
      .map(
        (farmers: Farmer[]) => {
          return farmers.filter(
            (farmer: Farmer) => {
              return !farmer.inCurrentCrop;
            }
          );
        }
      );
  }

  getFarmers(paging: {offset: number; limit: number}): Observable<Pagination> {
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('offset', paging.offset.toString());
    searchParams.append('limit', paging.limit.toString());
    options.params = searchParams;
    return this.http.get('/api/farmer/get', options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json() as Pagination;
      });
  }

  save(farmer: Farmer) {
    if (farmer.id) {
      return this.update(farmer);
    }
    return this.register(farmer);
  }

  register(farmer: Farmer) {
    const requestUrl = '/api/farmer/add';
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});

    const body = new URLSearchParams();
    body.append('farmerCode', farmer.farmerCode);
    body.append('firstName', farmer.firstName);
    body.append('lastName', farmer.lastName);
    body.append('phone', farmer.phone);
    body.append('email', farmer.email);
    body.append('address', farmer.address);
    body.append('provinceId', farmer.provinceId.toString());
    body.append('districtId', farmer.districtId.toString());
    body.append('wardId', farmer.wardId.toString());
    return this.http.post(requestUrl, body.toString(), options).map(
      response => {
        return response.json();
      }
    );
  }

  update(farmer: Farmer) {
    const id = encodeURIComponent(farmer.id.toString());
    const requestUrl = `/api/farmer/update/${id}`;
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});

    const body = new URLSearchParams();
    body.append('farmerCode', farmer.farmerCode);
    body.append('firstName', farmer.firstName);
    body.append('lastName', farmer.lastName);
    body.append('phone', farmer.phone);
    body.append('email', farmer.email);
    body.append('address', farmer.address);
    body.append('provinceId', farmer.provinceId.toString());
    body.append('districtId', farmer.districtId.toString());
    body.append('wardId', farmer.wardId.toString());
    return this.http.post(requestUrl, body.toString(), options).map(
      response => {
        return response.json();
      }
    );
  }

  get(farmerId: number) {
    return this.getAll().map(
      (farmers: Farmer[]) => {
        const f = farmers.find(farmer => farmer.id === farmerId);
        if (!f) {
          return Observable.throw(new Error('Page not found.'));
        }
        return f;
      }
    );
  }
}
