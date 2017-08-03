import { Injectable } from '@angular/core';
import { URLSearchParams, RequestOptions, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

import { HttpService } from '../core/services/http.service';
import { Pagination } from '../core/models/pagination';
import { MaterialRequest } from './material-request';

@Injectable()
export class MaterialRequestService {
  constructor(private http: HttpService) { }

  sync(): Observable<MaterialRequest[]> {
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('paging', false.toString());
    options.params = searchParams;
    return this.http.get('/api/material/request/get', options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        const json = response.json();
        return Array.isArray(json) && json.map((value) => MaterialRequest.assign(value)) || [];
      });
  }

  getAll(): Observable<MaterialRequest[]> {
    const subject = new Subject();
    this.getMaterialRequests({offset: 0, limit: 20})
      .subscribe(
        paginator => {
          const materialRequests = paginator.objects;
          if (paginator.total > 20) {
            this.getMaterialRequests({offset: 20, limit: paginator.total})
              .subscribe(
                paginator2 => {
                  subject.next(materialRequests.concat(paginator2.objects));
                }
              );
          } else {
            subject.next(materialRequests);
          }
        }
      );
    return subject.asObservable();
  }

  getMaterialRequests(paging: { offset: number; limit: number; }): Observable<Pagination> {
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('offset', paging.offset.toString());
    searchParams.append('limit', paging.limit.toString());
    options.params = searchParams;
    return this.http.get('/api/material/request/get', options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json() as Pagination;
      });
  }

  get(materialRequestId: number): Observable<MaterialRequest> {
    return this.sync()
      .map(
        (requests) => {
          return requests.find((request) => request.id === materialRequestId);
        }
      );
  }

  approve(request: MaterialRequest): Observable<MaterialRequest> {

    const requestUrl = `/api/material/approve`;
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    const body = [{
      id: request.id,
      requestMaterialDetails: request.requestMaterialDetails
    }];
    return this.http.post(requestUrl, JSON.stringify(body), options).map((response: Response) => {
      // TODO handle errors (eg. throw error exception)
      return response.json();
    });
  }
}
