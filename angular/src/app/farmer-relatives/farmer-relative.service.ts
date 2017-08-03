import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { HttpService } from '../core/services/http.service';
import { FarmerRelative } from '../core/models/farmerRelative';

@Injectable()
export class FarmerRelativeService {

  constructor(
    private http: HttpService
  ) { }

  getFarmerRelativeAll(id: number) {
    const idString = encodeURIComponent(id.toString());
    return this.http.get(`/api/farmer/${idString}/relative/get`)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }

  saveFarmerRelative(relative: FarmerRelative, id: number) {
    if (relative.id) {
      return this.updateFarmerRelative(relative, id);
    }
    return this.registerFarmerRelative(relative, id);
  }

  registerFarmerRelative(relative: FarmerRelative, id: number) {
    const idString = id.toString();
    const requestUrl = `/api/farmer/${idString}/relative/add`;
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});

    const body = new URLSearchParams();
    body.append('id', <any>relative.id);
    body.append('firstName', relative.firstName);
    body.append('lastName', relative.lastName);
    body.append('identityCard', relative.identityCard);
    body.append('phone', relative.phone);
    body.append('email', relative.email);
    body.append('address', relative.address);
    body.append('provinceId', relative.provinceId.toString());
    body.append('districtId', relative.districtId.toString());
    body.append('wardId', relative.wardId.toString());
    body.append('relationship', relative.relationship);
    return this.http.post(requestUrl, body.toString(), options).map(
      response => {
        return response.json();
      }
    );
  }

  updateFarmerRelative(relative: FarmerRelative, id: number) {
    const idString = id.toString();
    const requestUrl = `/api/farmer/${idString}/relative/update`;
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});

    const body = new URLSearchParams();
    body.append('id', <any>relative.id);
    body.append('firstName', relative.firstName);
    body.append('lastName', relative.lastName);
    body.append('identityCard', relative.identityCard);
    body.append('phone', relative.phone);
    body.append('email', relative.email);
    body.append('address', relative.address);
    body.append('provinceId', relative.provinceId.toString());
    body.append('districtId', relative.districtId.toString());
    body.append('wardId', relative.wardId.toString());
    body.append('relationship', relative.relationship.toString());
    body.append('uId', relative.uId.toString());
    body.append('delFlg', <any>relative.delFlg);
    return this.http.post(requestUrl, body.toString(), options).map(
      response => {
        return response.json();
      }
    );
  }

  deleteItem(relative: FarmerRelative, farmerId: number): Observable<boolean> {
    relative.delFlg = 1;
    // TODO: handle error
    return this.updateFarmerRelative(relative, farmerId);
  }
}
