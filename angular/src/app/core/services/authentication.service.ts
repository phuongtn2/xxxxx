import { Injectable } from '@angular/core';
import { Headers, Http, Request, RequestOptions, Response, URLSearchParams } from '@angular/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { User } from '../models/user';
import { UserCredential } from '../models/user_credential';

@Injectable()
export class AuthenticationService {
  private clientId = 'HY6BJIT58XGMN86UTM';
  private _credential: UserCredential;
  private _user: User;

  get credential(): UserCredential {
    if (this._credential) {
      return this._credential;
    }

    try {
      this._credential = JSON.parse(localStorage.getItem('credential'));
      this._credential = UserCredential.assign(this._credential);
    } catch (e) {
      this._credential = null;
    }
    return this._credential;
  }

  set credential(value: UserCredential) {
    this._credential = null;
    localStorage.setItem('credential', value.toString());
  }

  get currentUser(): User {
    if (this._user) {
      return this._user;
    }

    try {
      this._user = JSON.parse(localStorage.getItem('currentUser'));
      if (this._user) {
        this._user = User.assign(this._user);
      }
    } catch (e) {
      this._user = null;
    }
    return this._user;
  }

  set currentUser(value: User) {
    this._user = null;
    localStorage.setItem('currentUser', value.toString());
  }

  get rememberMe() {
    return localStorage.getItem('rememberLogin') === 'true';
  }

  set rememberMe(value) {
    localStorage.setItem('rememberLogin', value.toString());
  }

  constructor(
    private http: Http,
    private router: Router
  ) { }

  login(username: string, password: string) {
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});
    const body = new URLSearchParams();
    body.set('grant_type', 'password');
    body.set('client_id', this.clientId);
    body.set('username', username);
    body.set('password', password);
    return this.http.post('/api/oauth/token', body.toString(), options).map((response: Response) => {
      this.credential = UserCredential.assign(response.json());
    });
  }

  logout() {
    this.revokeToken();
    this.clearStorage();
  }

  getToken() {
    return this.credential ? this.credential.access_token : null;
  }

  unauthorizedHandler(request: Request) {
    return (response: Response) => {
      if (response.status === 401) {
        // handle authentication errors
        if (!this.rememberMe) {
          this.router.navigateByUrl('/login');
        } else {
          return this.refreshToken()
            .flatMap(
              () => {
                // update authorization token
                request.headers.set('Authorization', `Bearer ${this.getToken()}`);
                return this.http.request(request);
              }
          );
        }
      }
      return Observable.throw(response);
    };
  }

  private refreshToken() {
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});
    const body = new URLSearchParams();
    body.set('grant_type', 'refresh_token');
    body.set('client_id', this.clientId);
    body.set('refresh_token', this.credential.refresh_token);

    return this.http.post('/api/oauth/token', body.toString(), options)
      .map((response: Response) => {
        this.credential = UserCredential.assign(response.json());
      })
      .catch(
        (response: Response) => {
          this.router.navigateByUrl('/login');
          return Observable.throw(response);
        }
      );
  }

  private clearStorage() {
    localStorage.removeItem('credential');
    localStorage.removeItem('currentUser');
    localStorage.removeItem('rememberLogin');
    // TODO find another solution
    this._credential = null;
    this._user = null;
  }

  private revokeToken() {
    const headers = new Headers();
    const token = this.getToken();
    if (token) {
      headers.set('Authorization', `Bearer ${token}`);
      const options = new RequestOptions({headers: headers});
      return this.http.get('/api/oauth/token/revoke', options).subscribe();
    }
  }
}
