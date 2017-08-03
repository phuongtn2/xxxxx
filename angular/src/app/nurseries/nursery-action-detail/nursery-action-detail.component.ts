import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { MdDialog } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { NurseryActions, NurseryAction } from '../../core/enum/nursery-action.enum';
import { AuthenticationService } from '../../core/services/authentication.service';
import { Registration } from '../../crop-session/registration';
import { Nursery } from '../nursery';
import { NurseryActionDetail } from '../nursery-detail/nursery-action-detail';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { CommonService } from '../../core/services/common.service';
import { PestService } from '../../core/services/pest.service';
import { Pest } from '../../core/models/pest';
import { Material } from '../../materials/material';
import { MaterialService } from '../../materials/material.service';
import { NurseryService } from '../nursery.service';
import { MapDialogComponent } from '../../location/map-dialog/map-dialog.component';
import { ImagePopupComponent, VideoPopupComponent } from '../../shared';

@Component({
  selector: 'app-nursery-action-detail',
  templateUrl: './nursery-action-detail.component.html',
  styleUrls: ['./nursery-action-detail.component.scss'],
  providers: [
    CommonService,
    PestService,
    MaterialService,
    CropSessionService
  ]
})
export class NurseryActionDetailComponent implements OnInit {
  title: string;
  registration: Registration;
  private nursery: Nursery;
  nurseryAction: NurseryActionDetail;
  private nurseryActions;

  get NurseryAction() {
    return NurseryAction;
  }

  constructor(
    private titleService: Title,
    private location: Location,
    private dialog: MdDialog,
    private route: ActivatedRoute,
    private authService: AuthenticationService,
    private pestService: PestService,
    private nurseryService: NurseryService,
    private materialService: MaterialService,
    private cropSessionService: CropSessionService
  ) {
    this.title = 'Chi tiết hoạt động';
    this.nurseryActions = NurseryActions;
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    const farmerCode = this.route.snapshot.params['farmerCode'];
    const actionId = +this.route.snapshot.params['actionId'];
    this.cropSessionService.getRegistrationByFarmerCode(farmerCode)
      .subscribe(
        (registration: Registration) => {
          this.registration = registration;
          this.nurseryService.getNursery(registration.id, registration.nurseryId)
            .subscribe(
              (nursery: Nursery) => {
                this.nursery = nursery;
                this.nurseryAction = nursery.details && nursery.details.find(
                  (action: NurseryActionDetail) => {
                    return action.id === actionId;
                  }
                );
                if (!this.nurseryAction) {
                  return Observable.throw(new Error('Page not found!'));
                }
                this.getNurseryActionDetail();
              }
            );
        }
      );
  }

  openMapPopup() {
    const dialogRef = this.dialog.open(MapDialogComponent);
    dialogRef.componentInstance.location = {lat: this.nurseryAction.coordX, lng: this.nurseryAction.coordY};
  }

  getMediaFileUrl(fileId) {
    return `/api/file/download/${fileId}?access_token=${this.authService.getToken()}`;
  }

  isImage(fileType) {
    return fileType.indexOf('image/') !== -1;
  }

  isVideo(fileType) {
    return fileType.indexOf('video/') !== -1;
  }

  openImagePopup(srcUrl) {
    const dialogRef = this.dialog.open(ImagePopupComponent);
    dialogRef.componentInstance.srcUrl = srcUrl;
  }

  openVideoPopup(srcUrl) {
    const dialogRef = this.dialog.open(VideoPopupComponent);
    dialogRef.componentInstance.srcUrl = srcUrl;
  }

  goBack() {
    this.location.back();
  }

  private getNurseryActionDetail() {
    if (!this.nurseryAction.action) {
      return;
    }
    if (this.nurseryAction.action.toString() === NurseryAction.PesticidesSpraying.toString()) {
      this.nurseryAction.contents.forEach((v) => {
        this.materialService.get(v.pesticideId).subscribe((response: Material) => {
          v.materialName = response.name;
          v.materialUnit = response.unit;
        });
      });
    }

    if (this.nurseryAction.action.toString() === NurseryAction.Pest.toString()) {
      this.nurseryAction.contents.forEach((v) => {
        this.pestService.get(v.pestId).subscribe((response: Pest) => {
          v.pestName = response.name;
        });
      });
    }
  }
}
