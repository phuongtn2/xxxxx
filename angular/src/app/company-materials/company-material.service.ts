import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response, URLSearchParams } from '@angular/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

import { Pagination } from '../core/models/pagination';
import { HttpService } from '../core/services/http.service';
import { CompanyMaterial } from './company-material';

@Injectable()
export class CompanyMaterialService {
  private _key = 'companyMaterials';
  private get _materials() {
    const companyMaterials = JSON.parse(sessionStorage.getItem(this._key));
    if (!companyMaterials) {
      return null;
    }
    return companyMaterials.map(
      (material) => CompanyMaterial.assign(material)
    );
  }
  private set _materials(materials: any) {
    if (typeof materials !== 'string') {
      materials = JSON.stringify(materials);
    }
    sessionStorage.setItem(this._key, materials);
  }

  constructor(private http: HttpService) { }

  syncCompanyMaterials(): Observable<CompanyMaterial[]> {
    if (this._materials && this._materials.length) {
      const subject = new BehaviorSubject(this._materials);
      return subject.asObservable();
    }
    return this.http.get('/api/material/company/sync')
      .map(
        (response: Response) => {
          // TODO handle errors (eg. throw error exception)
          this._materials = response.json();
          return this._materials;
        }
      );
  }

  getAll(): Observable<CompanyMaterial[]> {
    const subject = new Subject();
    this.getCompanyMaterials({offset: 0, limit: 20})
      .subscribe(
        paginator => {
          const companyMaterials = paginator.objects;
          if (paginator.total > 20) {
            this.getCompanyMaterials({offset: 20, limit: paginator.total})
              .subscribe(
                paginator2 => {
                  subject.next(companyMaterials.concat(paginator2.objects));
                }
              );
          } else {
            subject.next(companyMaterials);
          }
        }
      );
    return subject.asObservable();
  }

  getCompanyMaterials(paging: {offset: number, limit: number}): Observable<Pagination> {
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('offset', paging.offset.toString());
    searchParams.append('limit', paging.limit.toString());
    options.params = searchParams;
    return this.http.get('/api/material/company/get', options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        // TODO consider using sync method
        const paginator = response.json() as Pagination;
        if (Array.isArray(paginator.objects)) {
          paginator.objects = paginator.objects.map(
            (item) => CompanyMaterial.assign(item)
          );
        }
        return paginator;
      });
  }

  get(id: number): Observable<CompanyMaterial> {
    return this.syncCompanyMaterials()
      .map(
        (materials: CompanyMaterial[]) => {
          return materials.find(
            (material: CompanyMaterial) => {
              return material.id === id;
            }
          );
        }
      );
  }

  save(material: CompanyMaterial): Observable<CompanyMaterial> {
    return !material.id ? this.add(material) : this.update(material);
  }

  add(material: CompanyMaterial): Observable<CompanyMaterial> {
    const id = encodeURIComponent(material.materialId);
    const requestUrl = `/api/material/${id}/add`;
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});

    const body = new URLSearchParams();
    body.set('status', 'INVESTMENT');
    body.set('price', material.price.toString());
    body.set('memo', material.memo);
    return this.http.post(requestUrl, body.toString(), options).map((response: Response) => {
      console.log('add response', response);
      // TODO handle errors (eg. throw error exception)
      return response.json();
    });
  }

  update(material: CompanyMaterial): Observable<CompanyMaterial> {
    const id = encodeURIComponent(String(material.id));
    const requestUrl = `/api/material/${id}/update`;
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});

    const body = new URLSearchParams();
    body.set('status', 'INVESTMENT');
    body.set('price', material.price.toString());
    body.set('memo', material.memo);
    return this.http.post(requestUrl, body.toString(), options).map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        console.log('update response', response);
        return response.json();
      });
  }

  delete(id: string): Observable<boolean> {
    id = encodeURIComponent(id);
    return this.http.delete(`/api/material/${id}/delete`)
      .map((r) => {
        console.log(r);
        // TODO handle errors (eg. throw error exception)
        return true;
      });
  }
}
