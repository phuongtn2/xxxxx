import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { MdDialog } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { MapDialogComponent } from '../../location/map-dialog/map-dialog.component';
import { ImagePopupComponent, VideoPopupComponent } from '../../shared';
import { AuthenticationService } from '../../core/services/authentication.service';
import { Registration } from '../../crop-session/registration';
import { Field } from '../field';
import { FieldPlot } from '../field-plot';
import { FieldService } from '../field.service';
import { Material } from '../../materials/material';
import { MaterialService } from '../../materials/material.service';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { FieldAction } from '../../core/enum/field-action.enum';
import { PlotDetail } from '../plot-detail';
import { CommonService } from '../../core/services/common.service';
import { PestService } from '../../core/services/pest.service';
import { Pest } from '../../core/models/pest';

@Component({
  selector: 'app-plot-action-detail',
  templateUrl: './plot-action-detail.component.html',
  styleUrls: ['./plot-action-detail.component.scss'],
  providers: [
    CommonService,
    PestService,
    MaterialService,
    CropSessionService
  ]
})
export class PlotActionDetailComponent implements OnInit {
  title: string;
  registration: Registration;
  plot: FieldPlot;
  plotAction: PlotDetail;
  seed: Material;

  get FieldAction() {
    return FieldAction;
  }

  constructor(
    private titleService: Title,
    private location: Location,
    private dialog: MdDialog,
    private fieldService: FieldService,
    private pestService: PestService,
    private route: ActivatedRoute,
    private authService: AuthenticationService,
    private materialService: MaterialService,
    private cropSessionService: CropSessionService
  ) {
    this.title = 'Chi tiết hoạt động';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    const farmerCode = this.route.snapshot.params['farmerCode'];
    this.cropSessionService.getRegistrationByFarmerCode(farmerCode)
      .subscribe(
        (registration: Registration) => {
          this.registration = registration;
          this.getField();
        }
      );
  }

  openMapPopup() {
    const dialogRef = this.dialog.open(MapDialogComponent);
    dialogRef.componentInstance.location = {lat: this.plotAction.latitude, lng: this.plotAction.longitude};
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

  private getField() {
    this.fieldService.getField(this.registration.id, this.registration.cultivationId)
      .subscribe(
        (field: Field) => {
          const plotId = +this.route.snapshot.params['plotId'];
          this.plot = field.fieldPlots && field.fieldPlots.find(
            plotDetail => plotDetail.id === plotId
          );
          if (!this.plot) {
            return Observable.throw(new Error('Page not found!'));
          }

          const actionId = +this.route.snapshot.params['actionId'];
          this.plotAction = this.plot.details.find(action => action.id === actionId);
          this.getFieldActionDetail(field);
          this.getSeed(field.seedId);
        }
      );
  }

  private getFieldActionDetail(field: Field) {
    if (!this.plotAction.action) {
      return;
    }
    if (
      this.plotAction.action.toString() === FieldAction.Manuring.toString() ||
      this.plotAction.action.toString() === FieldAction.PesticidesSpraying.toString()
    ) {
      this.plotAction.contents.forEach((v) => {
        this.materialService.get(v.materialId).subscribe((response: Material) => {
          v.materialName = response.name;
          v.materialUnit = response.unit;
        });
      });
    }

    if (this.plotAction.action.toString() === FieldAction.Pest.toString()) {
      this.plotAction.contents.forEach((v) => {
        this.pestService.get(v.pestId).subscribe((response: Pest) => {
          v.pestName = response.name;
        });
      });
    }
  }

  private getSeed(seedId: number) {
    this.materialService.get(seedId)
      .subscribe(
        (material) => {
          this.seed = material;
        }
      );
  }
}
