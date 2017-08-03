import { Injectable } from '@angular/core';
import { RequestOptions, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

import { Pagination } from '../core/models/pagination';
import { HttpService } from '../core/services/http.service';
import { Registration } from './registration';

@Injectable()
export class CropSessionService {

  constructor(
    private http: HttpService
  ) { }

  syncRegistrationList(params?: URLSearchParams): Observable<Registration[]> {
    const subject = new Subject();
    this.getRegistrations({offset: 0, limit: 20}, params)
      .subscribe(
        paginator => {
          const registrations = paginator.objects;
          if (paginator.total > 20) {
            this.getRegistrations({offset: 20, limit: paginator.total}, params)
              .subscribe(
                paginator2 => {
                  subject.next(registrations.concat(paginator2.objects));
                }
              );
          } else {
            subject.next(registrations);
          }
        }
      );
    return subject.asObservable();
  }

  getRegisteredRegistrations(): Observable<Registration[]> {
    const params = new URLSearchParams();
    params.set('isCancel', '0');
    return this.syncRegistrationList(params);
  }

  getRegistrations(paging: {offset: number; limit: number}, moreParams?: URLSearchParams) {
    const requestUrl = '/api/crop-session/farmer/get';
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('offset', paging.offset.toString());
    searchParams.append('limit', paging.limit.toString());
    if (moreParams) {
      searchParams.appendAll(moreParams);
    }
    options.params = searchParams;
    return this.http.get(requestUrl, options)
      .map(
        (response: Response) => {
          const paginator = response.json() as Pagination;
          if (Array.isArray(paginator.objects)) {
            paginator.objects = paginator.objects.map(value => Registration.assign(value));
          }
          return paginator;
        }
      );
  }

  getRegistration(id: number) {
    const encodedId = encodeURIComponent(String(id));
    const requestUrl = `/api/crop-session/farmer/get/${encodedId}`;
    return this.http.get(requestUrl)
      .map(
        (response: Response) => {
          return Registration.assign(response.json());
        }
      );
  }

  getRegistrationByFarmerCode(farmerCode: string) {
    return this.syncRegistrationList()
      .map(
        (registrations: Registration[]) => {
          return registrations.find(
            (registration) => {
              return registration.farmer.farmerCode.toLowerCase() === farmerCode.toLowerCase();
            }
          );
        }
      );
  }

  getByResponsibilityEmployee(employeeId: number) {
    return this.syncRegistrationList()
      .map(
        (registrations: Registration[]) => {
          return registrations.filter(
            (registration) => registration.responsibilityEmployeeId === employeeId
          );
        }
      );
  }
}
