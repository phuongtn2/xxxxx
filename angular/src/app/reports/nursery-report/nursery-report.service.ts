import { Injectable } from '@angular/core';
import { Response } from '@angular/http';

import { HttpService } from '../../core/services/http.service';

@Injectable()
export class NurseryReportService {

  constructor(private http: HttpService) { }

  getData() {
    const requestUrl = '/api/report/plant-area/get';
    return this.http.get(requestUrl)
      .map(
        (response: Response) => {
          return response.json();
        }
      );
  }

  exportReport() {
    const requestUrl = '/api/report/nursery';
    return this.http.open(requestUrl);
  }
}
