import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { HttpService } from './http.service';
import { ObserverZone } from '../models/observer-zone';
import { Zone } from '../models/zone';

@Injectable()
export class ZoneService {

  constructor(
    private http: HttpService
  ) { }

  sync() {
    return this.http.get('/api/zone/sync');
  }

  getZones(): Observable<Zone[]> {
    const requestUrl = '/api/zone/sync';
    return this.http.get(requestUrl)
      .map(
        (response: Response) => {
          let zones = response.json();
          if (Array.isArray(zones)) {
            zones = zones.map(value => Zone.assign(value));
            return zones;
          }
          return zones;
        }
      );
  }

  addZone(zone: ObserverZone) {
    const requestUrl = '/api/observer/add';
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});

    const body = new URLSearchParams();
    body.append('companyId', zone.companyId.toString());
    body.append('employeeId', zone.employeeId.toString());
    body.append('cultivationZoneId', zone.cultivationZoneId.toString());
    return this.http.post(requestUrl, body.toString(), options).map(
      response => {
        return response.json();
      }
    );
  }

  updateZone(zone: ObserverZone) {
    const requestUrl = '/api/observer/update';
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});

    const body = new URLSearchParams();
    body.append('id', zone.id.toString());
    body.append('companyId', zone.companyId.toString());
    body.append('employeeId', zone.employeeId.toString());
    body.append('cultivationZoneId', zone.cultivationZoneId.toString());
    return this.http.post(requestUrl, body.toString(), options).map(
      response => {
        return response.json();
      }
    );
  }

  saveZone(observerZone: ObserverZone) {
    if (observerZone.id) {
      return this.updateZone(observerZone);
    }
    return this.addZone(observerZone);
  }

}
