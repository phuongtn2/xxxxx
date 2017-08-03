import { Injectable } from '@angular/core';
import { Headers, Http, Request, RequestMethod, RequestOptions, RequestOptionsArgs, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { AuthenticationService } from './authentication.service';

@Injectable()
export class HttpService {

  constructor(
    private http: Http,
    private authService: AuthenticationService
  ) { }

  private _setHeaders(request: Request) {
    let headers = request && request.headers;
    if (!headers) {
      headers = new Headers();
      request.headers = headers;
    }

    headers.forEach(
      (values, name: string) => {
        if (name === 'Content-Type') {
          for (const index of Object.keys(values)) {
            // TODO read more about ; syntax
            values[index] = values[index].concat(';charset=utf8');
          }
        }
      }
    );
    const token = this.authService.getToken();
    if (token) {
      headers.set('Authorization', `Bearer ${token}`);
    }
  }

  /**
   * Performs any type of http request. First argument is required, and can either be a url or
   * a {@link Request} instance. If the first argument is a url, an optional {@link RequestOptions}
   * object can be provided as the 2nd argument. The options object will be merged with the values
   * of {@link BaseRequestOptions} before performing the request.
   */
  request(url: string | Request, options?: RequestOptionsArgs): Observable<Response> {
    let request: Request;
    if (typeof url === 'string') {
      options = options || new RequestOptions();
      options.url = url;
      request = new Request(options as RequestOptions);
    } else {
      request = url;
    }
    this._setHeaders(request);
    return this.http.request(request).catch(this.authService.unauthorizedHandler(request));
  }

  /**
   * Performs a request with `get` http method.
   */
  get(url: string, options?: RequestOptionsArgs): Observable<Response> {
    let request: Request;
    options = options || new RequestOptions();
    options.url = url;
    options.method = RequestMethod.Get;
    request = new Request(options as RequestOptions);
    this._setHeaders(request);
    return this.http.request(request).catch(this.authService.unauthorizedHandler(request));
  }

  /**
   * Performs a request with `post` http method.
   */
  post(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    let request: Request;
    options = options || new RequestOptions();
    options.url = url;
    options.method = RequestMethod.Post;
    options.body = body;
    request = new Request(options as RequestOptions);
    this._setHeaders(request);
    return this.http.request(request).catch(this.authService.unauthorizedHandler(request));
  }

  /**
   * Performs a request with `put` http method.
   */
  put(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    let request: Request;
    options = options || new RequestOptions();
    options.url = url;
    options.method = RequestMethod.Put;
    options.body = body;
    request = new Request(options as RequestOptions);
    this._setHeaders(request);
    return this.http.request(request).catch(this.authService.unauthorizedHandler(request));
  }

  /**
   * Performs a request with `delete` http method.
   */
  delete(url: string, options?: RequestOptionsArgs): Observable<Response> {
    let request: Request;
    options = options || new RequestOptions();
    options.url = url;
    options.method = RequestMethod.Delete;
    request = new Request(options as RequestOptions);
    this._setHeaders(request);
    return this.http.request(request).catch(this.authService.unauthorizedHandler(request));
  }

  /**
   * Performs a request with `patch` http method.
   */
  patch(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    let request: Request;
    options = options || new RequestOptions();
    options.url = url;
    options.method = RequestMethod.Patch;
    options.body = body;
    request = new Request(options as RequestOptions);
    this._setHeaders(request);
    return this.http.request(request).catch(this.authService.unauthorizedHandler(request));
  }

  /**
   * Performs a request with `head` http method.
   */
  head(url: string, options?: RequestOptionsArgs): Observable<Response> {
    let request: Request;
    options = options || new RequestOptions();
    options.url = url;
    options.method = RequestMethod.Head;
    request = new Request(options as RequestOptions);
    this._setHeaders(request);
    return this.http.request(request).catch(this.authService.unauthorizedHandler(request));
  }

  /**
   * Performs a request with `options` http method.
   */
  options(url: string, options?: RequestOptionsArgs): Observable<Response> {
    let request: Request;
    options = options || new RequestOptions();
    options.url = url;
    options.method = RequestMethod.Options;
    request = new Request(options as RequestOptions);
    this._setHeaders(request);
    return this.http.request(request).catch(this.authService.unauthorizedHandler(request));
  }

  open(url: string) {
    const hasQM = url.indexOf('?') !== -1;
    const requestUrl = url + (hasQM ? '&' : '?') + `access_token=${this.authService.getToken()}`;
    return window.open(requestUrl, '_blank');
  }
}
