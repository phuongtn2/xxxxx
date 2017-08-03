import { Injectable } from '@angular/core';
import { Response } from '@angular/http';

import { HttpService } from '../../core/services/http.service';
import { TechnicianZone } from './technician-zone';

@Injectable()
export class TechnicianZoneService {

  constructor(
    private http: HttpService
  ) { }

  getByTechnicianId(technicianId: number) {
    return this.http.get(`/api/employee-zone/sync/${encodeURIComponent(technicianId.toString())}`)
      .map(
        (response: Response) => {
          const json = response.json();
          if (Array.isArray(json)) {
            return json.map(value => TechnicianZone.assign(value));
          }
          return [];
        }
      );
  }

  sync(employeeId: number, data: any) {
    return this.http.post(`/api/employee-zone/sync/${encodeURIComponent(employeeId.toString())}`, data)
      .map(
        (response: Response) => {
          return response.json();
        }
      );
  }
}
