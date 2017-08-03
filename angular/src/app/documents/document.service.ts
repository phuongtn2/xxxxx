import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

import { Pagination } from '../core/models/pagination';
import { HttpService } from '../core/services/http.service';
import { Document } from './document';

@Injectable()
export class DocumentService {
  constructor(private http: HttpService) { }

  getAll(): Observable<Document[]> {
    const subject = new Subject();
    this.getDocuments({offset: 0, limit: 20})
      .subscribe(
        paginator => {
          const documents = paginator.objects;
          if (paginator.total > 20) {
            this.getDocuments({offset: 20, limit: paginator.total})
              .subscribe(
                paginator2 => {
                  subject.next(documents.concat(paginator2.objects));
                }
              );
          } else {
            subject.next(documents);
          }
        }
      );
    return subject.asObservable();
  }

  getDocuments(paging: {offset: number, limit: number}): Observable<Pagination> {
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('offset', paging.offset.toString());
    searchParams.append('limit', paging.limit.toString());
    options.params = searchParams;

    return this.http.get('/api/document/get', options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }

  getDocument(id: string): Observable<Document> {
    id = encodeURIComponent(id);
    return this.http.get('/api/document/' + id)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }

  saveDocument(doc: Document): Observable<Document> {
    console.log('DocumentService saveDocument()');
    let formData = null;
    let requestUrl = '/api/document/add';
    let options = null;

    if (doc.id) { // save existing document
      requestUrl = '/api/document/update/' + doc.id.toString();
      const headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' });
      options = new RequestOptions({ headers: headers });
      formData = new URLSearchParams();
      formData.set('documentName', doc.documentName);
    } else {
      formData = new FormData();
      formData.append('file', doc.file);
      formData.append('name', doc.documentName);
    }

    console.log(formData);
    return this.http.post(requestUrl, formData, options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
    }

  deleteDocument(id: number) {
    const encodedId = encodeURIComponent(<any>id);
    return this.http.delete(`/api/document/delete/${encodedId}`)
      .map(() => {
        // TODO handle errors (eg. throw error exception)
        return true;
      });
  }

  downloadDocument(id: string) {
    id = encodeURIComponent(id);
    return this.http.open(`/api/document/download/${id}`);
  }
}
