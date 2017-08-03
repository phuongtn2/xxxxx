import { Injectable } from '@angular/core';
import { Response, URLSearchParams, RequestOptions } from '@angular/http';

import { HttpService } from '../../core/services/http.service';

@Injectable()
export class TaskMoveActionReportService {

  constructor(
    private http: HttpService
  ) { }

  getData(technicianId, startDate, endDate) {
    technicianId = encodeURIComponent(technicianId);
    startDate = new Date(startDate);
    endDate = new Date(endDate);
    endDate.setDate(endDate.getDate() + 1);
    const requestUrl = `/api/report/task/${technicianId}/get`;
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('startDateReport', startDate.getTime());
    searchParams.append('endDateReport', endDate.getTime());
    options.params = searchParams;
    return this.http.get(requestUrl, options)
      .map(
        (response: Response) => {
          return response.json();
        }
      );
  }

  exportReport(technicianId, startDate, endDate) {
    technicianId = encodeURIComponent(technicianId);
    startDate = new Date(startDate);
    endDate = new Date(endDate);
    endDate.setDate(endDate.getDate() + 1);
    let requestUrl = `/api/report/task/${technicianId}`;
    const searchParams = new URLSearchParams();
    searchParams.append('startDateReport', startDate.getTime());
    searchParams.append('endDateReport', endDate.getTime());
    requestUrl = requestUrl.concat('?', searchParams.toString());
    return this.http.open(requestUrl);
  }
}
