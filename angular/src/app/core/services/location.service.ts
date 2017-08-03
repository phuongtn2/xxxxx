import { Inject, Injectable, LOCALE_ID } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { HttpService } from './http.service';
import { District, Province, Ward } from '../models/location';

@Injectable()
export class LocationService {

  constructor(
    @Inject(LOCALE_ID) private localeId: string,
    private http: HttpService
  ) { }

  getProvinces(): Observable<Province[]> {
    const requestUrl = '/api/location/province/get';
    return this.http.get(requestUrl)
      .map(
        (response: Response) => {
          // TODO: handle exception
          const json = response.json();
          if (Array.isArray(json)) {
            const collator = new Intl.Collator(this.localeId, {numeric: true});
            return json.map(value => Province.assign(value)).sort((p1, p2) => collator.compare(p1.name, p2.name));
          }
          return [];
        }
      );
  }

  getProvince(provinceId: number): Observable<Province> {
    return this.getProvinces()
      .map(
        (provinces) => provinces.find(
          province => province.id === provinceId
        )
      );
  }

  getDistricts(provinceId: number) {
    const id = encodeURIComponent(String(provinceId));
    const requestUrl = `/api/location/${id}/district/get`;

    return this.http.get(requestUrl)
      .map(
        (response: Response) => {
          // TODO: handle exception
          const json = response.json();
          if (Array.isArray(json)) {
            const collator = new Intl.Collator(this.localeId, {numeric: true});
            return json.map(value => District.assign(value)).sort((d1, d2) => collator.compare(d1.name, d2.name));
          }
          return [];
        }
      );
  }

  getAllDistricts(): Observable<District[]> {
    const requestUrl = '/api/location/district/get';
    return this.http.get(requestUrl)
      .map(
        (response: Response) => {
          const json = response.json();
          return Array.isArray(json) && json.map(value => District.assign(value)) || [];
        }
      );
  }

  getDistrict(districtId: number, provinceId: number): Observable<District> {
    return this.getDistricts(provinceId)
      .map(
        districts => districts.find(district => district.id === districtId)
      );
  }

  getWards(districtId: number) {
    const id = encodeURIComponent(String(districtId));
    const requestUrl = `/api/location/${id}/ward/get`;

    return this.http.get(requestUrl)
      .map(
        (response: Response) => {
          // TODO: handle exception
          const json = response.json();
          if (Array.isArray(json)) {
            const collator = new Intl.Collator(this.localeId, {numeric: true});
            return json.map(value => Ward.assign(value)).sort((w1, w2) => collator.compare(w1.name, w2.name));
          }
          return [];
        }
      );
  }

  getWard(wardId: number, districtId: number): Observable<Ward> {
    return this.getWards(districtId)
      .map(
        wards => wards.find(ward => ward.id === wardId)
      );
  }
}
