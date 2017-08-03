import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { User } from '../core/models/user';
import { HttpService } from '../core/services/http.service';
import { AuthenticationService } from '../core/services/authentication.service';

@Injectable()
export class SettingService {

  constructor(
    private authService: AuthenticationService,
    private http: HttpService
  ) { }

  getUserInfo(): Observable<User> {
    return this.http.get('/api/me')
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        const r = response.json();
        const user = Array.isArray(r.data) && User.assign(r.data[0]) || null;
        this.authService.currentUser = user;
        return user;
      });
  }

  changeName(firstName: string, lastName: string): Observable<User> {

    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});
    const body = new URLSearchParams();
    body.set('firstName', firstName);
    body.set('lastName', lastName);
    return this.http.post('/api/me/update-info', body.toString(), options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }

  changePassword(currentPassword: string, newPassword: string, confirmPassword: string): Observable<User> {

    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    // headers.set('Authorization', `Bearer ${this.http.getToken()}`);
    const options = new RequestOptions({headers: headers});
    const body = new URLSearchParams();
    body.set('currentPassword', currentPassword);
    body.set('newPassword', newPassword);
    body.set('confirmPassword', confirmPassword);
    return this.http.post('/api/me/change-password', body.toString(), options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }
}
