import { Injectable } from '@angular/core';
import { RequestOptions, Response, URLSearchParams } from '@angular/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';

import { Material } from './material';
import { HttpService } from '../core/services/http.service';
import { Pagination } from '../core/models/pagination';

@Injectable()
export class MaterialService {
  private _key = 'materials';
  private get _materials() {
    const materials = JSON.parse(sessionStorage.getItem(this._key));
    if (!materials) {
      return null;
    }
    return materials.map(
      (material) => Material.assign(material)
    );
  }
  private set _materials(materials: any) {
    if (typeof materials !== 'string') {
      materials = JSON.stringify(materials);
    }
    sessionStorage.setItem(this._key, materials);
  }

  constructor(private http: HttpService) { }

  getAll(): Observable<Material[]> {
    if (this._materials && this._materials.length) {
      const subject = new BehaviorSubject(this._materials);
      return subject.asObservable();
    }
    return this.http.get('/api/material/get')
      .map(
        (response: Response) => {
          // TODO handle errors (eg. throw error exception)
          this._materials = response.json();
          return this._materials;
        }
      );
  }

  getMaterials(paging: {offset: number, limit: number}): Observable<Pagination> {
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('offset', paging.offset.toString());
    searchParams.append('limit', paging.limit.toString());
    options.params = searchParams;
    return this.http.get('/api/material/get', options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }

  get(id: number): Observable<Material> {
    return this.getAll()
      .map(
        (materials: Material[]) => {
          return materials.find(
            (material: Material) => {
              return material.id === id;
            }
          );
        }
      );
  }

  saveMaterial(material: Material): Observable<Material> {
    let requestUrl = '/api/material';
    if (material.id) { // save existing material
      const id = encodeURIComponent(String(material.id));
      requestUrl += '/' + id;
    }
    return this.http.get(requestUrl)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }

  getSeeds(): Observable<Array<Material>> {
    const requestUrl = '/api/material/get';
    const options = new RequestOptions();
    const searchParams = new URLSearchParams();
    searchParams.append('subType', 'seed');
    options.params = searchParams;
    return this.http.get(requestUrl, options)
      .map(
        (response: Response) => {
          const r = response.json();
          if (Array.isArray(r)) {
            return r.map(value => Material.assign(value));
          }
          return [];
        }
      );
  }
}
