import { Injectable } from '@angular/core';
import { Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';

import { Division } from '../core/models/division';
import { RoleSlug } from '../core/enum/role-slug.enum';
import { AuthenticationService } from '../core/services/authentication.service';
import { HttpService } from '../core/services/http.service';
import { Pagination } from '../core/models/pagination';
import { User } from '../core/models/user';
import { Department } from './department';

@Injectable()
export class UserService {
  private _users: User[];

  constructor(
    private http: HttpService,
    private authService: AuthenticationService
  ) { }

  getAll() {
    if (this._users) {
      return new BehaviorSubject(this._users).asObservable();
    }
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('paging', false.toString());
    options.params = searchParams;
    return this.http.get('/api/employee/get', options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        const r = response.json();
        if (Array.isArray(r)) {
          this._users = r.map(value => User.assign(value));
          return this._users;
        }
        return r;
      });
  }

  getUsers(paging: {offset: number, limit: number}): Observable<Pagination> {
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('offset', paging.offset.toString());
    searchParams.append('limit', paging.limit.toString());
    options.params = searchParams;
    return this.http.get('/api/employee/get', options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }

  save(user: User) {
    if (user.id) {
      return this.update(user);
    }
    return this.register(user);
  }

  register(user: User): Observable<User> {
    const requestUrl = '/api/employee/add';
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});
    const body = new URLSearchParams();
    body.append('companyId', this.authService.currentUser.companyId.toString());
    body.append('firstName', user.firstName);
    body.append('lastName', user.lastName);
    body.append('phone', user.phone);
    body.append('email', user.email);
    body.append('address', user.address);
    body.append('provinceId', user.provinceId.toString());
    body.append('districtId', user.districtId.toString());
    body.append('wardId', user.wardId.toString());
    body.append('departmentId', user.departmentId.toString());
    body.append('divisionId', user.divisionId.toString());
    body.append('userId', user.userId);
    body.append('password', user.password);
    body.append('roleSlug', user.roleSlug.toString());
    body.append('position', user.position.toString());
    body.append('employeeCode', user.employeeCode);
    return this.http.post(requestUrl, body.toString(), options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }

  update(user: User): Observable<User> {
    const id = encodeURIComponent(user.id.toString());
    const requestUrl = `/api/employee/update/${id}`;
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});
    const body = new URLSearchParams();
    body.append('firstName', user.firstName);
    body.append('lastName', user.lastName);
    body.append('phone', user.phone);
    body.append('email', user.email);
    body.append('address', user.address);
    body.append('provinceId', user.provinceId.toString());
    body.append('districtId', user.districtId.toString());
    body.append('wardId', user.wardId.toString());
    return this.http.post(requestUrl, body.toString(), options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }

  /**
   * // NOT USED
   * @param id
   * @returns {Observable<boolean>}
   */
  deleteUser(id: number): Observable<boolean> {
    return this.http.get('/api/employee/delete/' + id)
      .map(() => {
        // TODO handle errors (eg. throw error exception)
        return true;
      });
  }

  getResponsibilityEmployees() {
    const requestUrl = '/api/employee/get';
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('paging', false.toString());
    searchParams.append('role', RoleSlug.Technician.value);
    options.params = searchParams;
    return this.http.get(requestUrl, options)
      .map(
        (response: Response) => {
          let r = response.json();
          if (Array.isArray(r)) {
            r = r.map(user => User.assign(user));
          }
          return r;
        }
      );
  }

  get(userId: number) {
    return this.getAll().map(
      (users: User[]) => {
        const u = users.find(user => user.id === userId);
        if (!u) {
          return Observable.throw(new Error('Page not found.'));
        }
        return u;
      }
    );
  }

  getDivisionAll() {
    return this.http.get('/api/division/get')
      .map(
        (response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }

  getDepartments() {
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('paging', false.toString());
    options.params = searchParams;
    return this.http.get('/api/department/get', options)
      .map(
        (response: Response) => {
          const r = response.json();
          return Array.isArray(r) && r.map((value) => Department.assign(value)) || [];
        }
      );
  }

  getDivisions(departmentId) {
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('paging', false.toString());
    searchParams.append('departmentId', departmentId);
    options.params = searchParams;
    return this.http.get('/api/division/get', options)
      .map(
        (response: Response) => {
          const r = response.json();
          return Array.isArray(r) && r.map((value) => Division.assign(value)) || [];
        }
      );
  }
}
