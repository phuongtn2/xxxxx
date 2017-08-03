import { Component, Input, OnInit } from '@angular/core';
import { MdDialog } from '@angular/material';

import { Farmer } from '../../core/models/farmer';
import { FarmerRelative } from '../../core/models/farmerRelative';
import { Pagination } from '../../core/models/pagination';
import { DialogService } from '../../core/services/dialog.service';
import { NotificationService } from '../../core/services/notification.service';
import { FarmerRelativeService } from '../farmer-relative.service';
import { FarmerRelativeRegisterDialogComponent } from '../farmer-relative-register-dialog/farmer-relative-register-dialog.component';

@Component({
  selector: 'app-farmer-relative-list',
  templateUrl: './farmer-relative-list.component.html',
  styleUrls: ['./farmer-relative-list.component.scss']
})
export class FarmerRelativeListComponent implements OnInit {
  farmerRelatives: Array<FarmerRelative>;
  @Input() farmer: Farmer;

  constructor(
    private dialog: MdDialog,
    private dialogService: DialogService,
    private notification: NotificationService,
    private farmerRelativeService: FarmerRelativeService
  ) { }

  ngOnInit() {
    this.farmerRelatives = this.farmer.farmerRelatives;
  }

  refreshListFarmerRelatives() {
    this.farmerRelativeService.getFarmerRelativeAll(this.farmer.id)
      .subscribe(
        (farmerRelative: Pagination) => {
          this.farmerRelatives = farmerRelative.objects;
        }
      );
  }

  openDialog(relative?) {
    const dialogRef = this.dialog.open(FarmerRelativeRegisterDialogComponent);
    dialogRef.componentInstance.address = this.getAddress();
    dialogRef.componentInstance.farmerId = this.farmer.id;
    if (relative) {
      dialogRef.componentInstance.relative = relative;
      dialogRef.componentInstance.isEditing = true;
    }

    dialogRef.afterClosed().subscribe((newFarmerRelative) => {
      if (newFarmerRelative) {
        this.refreshListFarmerRelatives();
      }
    });
  }

  deleteItem(relative: FarmerRelative) {
    this.dialogService.confirm(null, 'Xóa nhân thân này?')
      .subscribe(
        result => {
          if (result) {
            this.farmerRelativeService.deleteItem(relative, this.farmer.id)
              .subscribe(
                deleteOk => {
                  if (deleteOk) {
                    this.notification.notify(`${relative.fullName} đã được xóa.`);
                    // refresh page
                    this.refreshListFarmerRelatives();
                  }
                }
              );
          }
        });
  }

  getAddress() {
    return {
      address: this.farmer.address,
      provinceId: this.farmer.provinceId,
      districtId: this.farmer.districtId,
      wardId: this.farmer.wardId
    };
  }
}
