import { Injectable } from '@angular/core';
import { Response } from '@angular/http';

import { HttpService } from './http.service';

@Injectable()
export class ObserverService {

  constructor(
    private http: HttpService
  ) { }

  getAll() {
    return this.http.get('api/observer/get?paging=false').map(
      (data: Response) => {
        return data.json();
      }
    );
  }

  getByEmployeeId(employeeId) {
    return this.getAll().map(
      (array) => {
        return array.filter(f => f.employeeId === employeeId);
      }
    );
  }
}
