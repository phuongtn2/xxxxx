import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Response } from '@angular/http';
import { MdDialog } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs/Observable';

import { CompanyMaterial } from '../company-material';
import { ServerError } from '../../core/objects/server-error';
import { ErrorCode } from '../../core/enum/error-code.enum';
import { CompanyMaterialService } from '../company-material.service';
import { MaterialDetailDialogComponent } from '../material-detail-dialog/material-detail-dialog.component';
import { DialogService } from '../../core/services/dialog.service';

@Component({
  selector: 'app-materials',
  templateUrl: './materials.component.html',
  styleUrls: ['./materials.component.scss'],
  providers: [CompanyMaterialService]
})
export class MaterialsComponent implements OnInit {
  title: string;
  companyMaterials: CompanyMaterial[];
  limit = 10;

  constructor(
    private titleService: Title,
    private dialog: MdDialog,
    private viewContainerRef: ViewContainerRef,
    private companyMaterialService: CompanyMaterialService,
    private dialogService: DialogService
  ) {
    this.title = 'Danh sách vật tư';
  }

  ngOnInit() {
    this.setTitle();
    this.getCompanyMaterials();
  }

  deleteMaterial(material) {
    this.dialogService.confirm(null, 'Xóa vật tư này?', this.viewContainerRef)
      .subscribe(result => {
        if (result === true) {
          this.companyMaterialService.delete(material.id)
            .catch(
              (response: Response) => {
                if (response.status === 422) {
                  const err: ServerError = response.json();
                  if (err.error && err.error.toString() === ErrorCode.DatabaseException.toString()) {
                  }
                }

                return Observable.throw(response);
              }
            )
            .subscribe(
              (isDeleted) => {
                if (isDeleted) {
                  this.getCompanyMaterials();
                }
              }
            );
        }
      });
  }

  openDialog(material?: CompanyMaterial) {
    const dialogRef = this.dialog.open(MaterialDetailDialogComponent);
    if (material) {
      dialogRef.componentInstance.material = material;
      dialogRef.componentInstance.isEditing = true;
    }
    dialogRef.afterClosed().subscribe((newMaterial) => {
      this.setTitle();
      if (newMaterial) {
        this.getCompanyMaterials();
      }
    });
  }

  private getCompanyMaterials() {
    this.companyMaterialService.getAll().subscribe(
      companyMaterials => {
        this.companyMaterials = companyMaterials;
      }
    );
  }

  private setTitle() {
    this.titleService.setTitle(this.title);
  }
}
