import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

import { HttpService } from '../core/services/http.service';
import { Pagination } from '../core/models/pagination';
import { FarmerCost } from './farmer-cost';

@Injectable()
export class FarmerCostService {
  constructor(
    private http: HttpService
  ) { }

  getAll(): Observable<FarmerCost[]> {
    const subject = new Subject();
    this.getFarmerCosts({offset: 0, limit: 20})
      .subscribe(
        paginator => {
          const farmerCosts = paginator.objects;
          if (paginator.total > 20) {
            this.getFarmerCosts({offset: 20, limit: paginator.total})
              .subscribe(
                paginator2 => {
                  subject.next(farmerCosts.concat(paginator2.objects));
                }
              );
          } else {
            subject.next(farmerCosts);
          }
        }
      );
    return subject.asObservable();
  }

  getFarmerCosts(paging: {offset: number; limit: number}): Observable<Pagination> {
    const requestUrl = '/api/cost/get';
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('offset', paging.offset.toString());
    searchParams.append('limit', paging.limit.toString());
    options.params = searchParams;
    return this.http.get(requestUrl, options)
      .map((response: Response) => {
        const r = response.json();
        localStorage.setItem('farmerCosts', JSON.stringify(r));
        if (Array.isArray(r.objects)) {
          r.objects = r.objects.map(obj => FarmerCost.assign(obj));
        }
        return r;
      });
  }

  /**
   *
   * @param farmerCosts
   * @param isEdit
   * @returns {Observable<boolean>}
   * @deprecated Switch to saveCost method.
   */
  save(farmerCosts: FarmerCost[], isEdit: boolean) {
    if (isEdit === true) {
      return this.update(farmerCosts);
    }
    return this.register(farmerCosts);
  }

  saveCost(cost: FarmerCost) {
    const costArray = [cost];
    if (cost.id) {
      return this.update(costArray);
    }
    return this.register(costArray);
  }

  register(farmerCosts: FarmerCost[]) {
    const requestUrl = '/api/cost/add';
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    return this.http.post(requestUrl, farmerCosts, options)
      .map(
        (response: Response) => {
          console.log('register crop farmer', response);
        }
      );
  }

  update(farmerCosts: FarmerCost[]) {
    const requestUrl = '/api/cost/update';
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    return this.http.post(requestUrl, farmerCosts, options)
      .map(
        (response: Response) => {
          console.log('update crop farmer', response);
        }
      );
  }

}
