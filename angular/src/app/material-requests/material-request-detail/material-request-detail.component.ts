import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Response } from '@angular/http';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/zip';

import { NotificationService } from '../../core/services/notification.service';
import { AuthenticationService } from '../../core/services/authentication.service';
import { RoleSlug } from '../../core/enum/role-slug.enum';
import { Material } from '../../materials/material';
import { MaterialService } from '../../materials/material.service';
import { MaterialRequest } from '../material-request';
import { MaterialRequestDetail } from '../material-request-detail';
import { MaterialRequestService } from '../material-request.service';

@Component({
  selector: 'app-material-request-detail',
  templateUrl: './material-request-detail.component.html',
  styleUrls: ['./material-request-detail.component.scss'],
  providers: [MaterialService, MaterialRequestService]
})
export class MaterialRequestDetailComponent implements OnInit {
  title: string;
  materialRequest: MaterialRequest;
  requestMaterialDetails: MaterialRequestDetail[];

  constructor(
    private location: Location,
    private route: ActivatedRoute,
    private notification: NotificationService,
    private router: Router,
    private materialRequestService: MaterialRequestService,
    private materialService: MaterialService,
    private authService: AuthenticationService,
    private titleService: Title
  ) {
    this.title = 'Danh sách vật tư';
  }

  ngOnInit() {
    this.setTitle();

    // get and fill register data if view/edit
    const id = +this.route.snapshot.params['id'];
    this.getMaterialRequestById(id);
  }

  private setTitle() {
    this.titleService.setTitle(this.title);
  }

  getMaterialRequestById(id: number) {
    Observable.zip(
      this.materialRequestService.get(id),
      this.materialService.getAll(),
      (materialRequest: MaterialRequest, materials) => {
        if (Array.isArray(materialRequest.requestMaterialDetails)) {
          materialRequest.requestMaterialDetails.map(
            (requestDetail) => {
              const m = materials.find((material: Material) => requestDetail.materialId === material.id);
              return requestDetail.material = m;
            }
          );
        }

        return materialRequest;
      }
    ).subscribe(
      (materialRequest) => {
        this.materialRequest = materialRequest;
        this.requestMaterialDetails = materialRequest.requestMaterialDetails;
      }
    );
  }

  approveRequest() {
    const data = new MaterialRequest();
    data.id = this.materialRequest.id;
    data.requestMaterialDetails = [];
    this.materialRequest.requestMaterialDetails
      .forEach((value) => {
        const requestDetail = new MaterialRequestDetail();
        requestDetail.id = value.id;
        requestDetail.requestMaterialId = value.requestMaterialId;
        // TODO(dtrthi): will edit this role
        if (this.authService.currentUser.roles.find(
          role => {
            const slug = role.slug && role.slug.toString();
            return slug === RoleSlug.TechnicianLeader.toString() || slug === RoleSlug.Director.toString();
          }
        )) {
          requestDetail.approveLv1Quantity = value.requestQuantity;
          requestDetail.approvedLv1Date = Date.now();
        }
        if (this.authService.currentUser.roles.find(role => role.slug && role.slug.toString() === RoleSlug.Storekeeper.toString())) {
          requestDetail.approveLv2Quantity = value.requestQuantity;
          requestDetail.approvedLv2Date = Date.now();
        }
        data['requestMaterialDetails'].push(requestDetail);
      });
    this.materialRequestService.approve(data)
      .catch(
        (response: Response) => {
          this.notification.notify('Xác nhận thất bại.');
          return Observable.throw(response);
        }
      ).subscribe(
        (/*response: MaterialRequest*/) => {
          this.notification.notify('Xác nhận thành công.');
          this.router.navigate(['./material-request']);
        }
      );
  }

  goBack() {
    this.location.back();
  }
}
