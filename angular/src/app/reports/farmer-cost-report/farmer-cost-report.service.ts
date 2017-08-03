import { Injectable } from '@angular/core';
import { Response } from '@angular/http';

import { HttpService } from '../../core/services/http.service';

@Injectable()
export class FarmerCostReportService {

  constructor(
    private http: HttpService
  ) { }

  getData(companyId) {
    companyId = encodeURIComponent(companyId);
    const requestUrl = `/api/report/farmer-cost/get/${companyId}`;
    return this.http.get(requestUrl)
      .map(
        (response: Response) => {
          return response.json();
        }
      );
  }

  exportReport(companyId) {
    companyId = encodeURIComponent(companyId);
    const requestUrl = `/api/report/farmer-cost/${companyId}`;
    return this.http.open(requestUrl);
  }
}
