import { Injectable } from '@angular/core';
import { Response } from '@angular/http';

import { HttpService } from '../../core/services/http.service';

@Injectable()
export class TaskPlanReportService {

  constructor(
    private http: HttpService
  ) { }

  getData(technicianId) {
    technicianId = encodeURIComponent(technicianId);
    const requestUrl = `/api/report/task-plan/${technicianId}/get`;
    return this.http.get(requestUrl)
      .map(
        (response: Response) => {
          return response.json();
        }
      );
  }

  exportReport(technicianId) {
    technicianId = encodeURIComponent(technicianId);
    const requestUrl = `/api/report/task-plan/${technicianId}`;
    return this.http.open(requestUrl);
  }
}
