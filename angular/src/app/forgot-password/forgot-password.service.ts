import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions, URLSearchParams } from '@angular/http';

@Injectable()
export class ForgotPasswordService {

  constructor(private http: Http) { }

  getCode(email: string) {
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('email', email);
    options.params = searchParams;
    return this.http.get('/api/reset-password', options);
  }

  resetPassword(confirmCode: string, newPassword: string, confirmPassword: string) {
    const options = new RequestOptions({
      headers: new Headers({'Content-Type': 'application/x-www-form-urlencoded'})
    });
    const searchParams = new URLSearchParams();
    searchParams.append('clientId', 'HY6BJIT58XGMN86UTM');
    searchParams.append('code', confirmCode);
    options.params = searchParams;
    return this.http.post('/api/password', {
      newPassword: newPassword,
      confirmPassword: confirmPassword
    }, options);
  }
}
