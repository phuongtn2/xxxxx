import { Injectable } from '@angular/core';
import { RequestOptions, Response, URLSearchParams } from '@angular/http';
import { Subject } from 'rxjs/Subject';

import { HttpService } from './http.service';
import { Pagination } from '../models/pagination';
import { Pest } from '../models/pest';

@Injectable()
export class CommonService {
  private _pests: Pest[];

  constructor(
    private http: HttpService
  ) { }

  getPest() {
    const subject = new Subject();
    const requestUrl = '/api/pest/get';
    const options = new RequestOptions();
    const body = new URLSearchParams();
    body.append('offset', '0');
    body.append('limit', '20');
    options.body = body;
    this.http.get(requestUrl, body)
      .subscribe(
        (firstResponse: Response) => {
          const firstPaginator = firstResponse.json() as Pagination;
          if (Array.isArray(firstPaginator.objects)) {
            this._pests = firstPaginator.objects.map((obj) => Pest.assign(obj));
          }

          body.set('offset', '20');
          body.set('limit', firstPaginator.total.toString());
          return this.http.get(requestUrl, options)
            .subscribe(
              (secondResponse: Response) => {
                const secondPaginator = secondResponse.json() as Pagination;
                if (Array.isArray(secondPaginator.objects)) {
                  this._pests.concat(secondPaginator.objects.map((obj) => Pest.assign(obj)));
                }
                subject.next(this._pests);
              }
            );
        }
      );
    return subject.asObservable();
  }
}
