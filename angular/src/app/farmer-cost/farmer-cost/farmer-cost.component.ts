import { Component, OnInit } from '@angular/core';
import { MdDialog } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/zip';

import { AuthenticationService } from '../../core/services/authentication.service';
import { Registration } from '../../crop-session/registration';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { FarmerCostService } from '../farmer-cost.service';
import { FarmerCostDetailDialogComponent } from '../farmer-cost-detail-dialog/farmer-cost-detail-dialog.component';
import { FarmerCost } from '../farmer-cost';

@Component({
  selector: 'app-farmer-cost',
  templateUrl: './farmer-cost.component.html',
  styleUrls: ['./farmer-cost.component.scss'],
  providers: [CropSessionService]
})
export class FarmerCostComponent implements OnInit {
  title: string;
  farmerCosts: FarmerCost[];
  limit = 10;
  registrations: Registration[];

  constructor(
    private titleService: Title,
    private authService: AuthenticationService,
    private dialog: MdDialog,
    private cropSessionService: CropSessionService,
    private farmerCostService: FarmerCostService
  ) {
      this.title = 'Danh sách chi phí nông dân';
  }

  ngOnInit() {
    this.setTitle();
    this.getFarmerCosts();
  }

  onRowClick(event) {
    this.openDetailDialog(event);
  }

  openDetailDialog(cost?) {
    const dialogRef = this.dialog.open(FarmerCostDetailDialogComponent);
    if (cost) {
      dialogRef.componentInstance.farmerCost = FarmerCost.assign(cost);
    }
    dialogRef.afterClosed()
      .subscribe(
        newCost => {
          if (newCost) {
            this.refreshCostList();
          }
        }
      );
  }

  private getFarmerCosts() {
    Observable.zip(
      this.farmerCostService.getAll(),
      this.cropSessionService.getByResponsibilityEmployee(this.authService.currentUser.id),
      (farmerCosts, registrations) => {
        this.registrations = registrations;
        if (Array.isArray(farmerCosts)) {
          farmerCosts.forEach((value: FarmerCost) => {
            value.registration = registrations.find(reg => reg.id === value.registrationId);
          });
        }
        this.farmerCosts = farmerCosts;
      }).subscribe();
  }

  private refreshCostList() {
    this.getFarmerCosts();
  }

  private setTitle() {
    this.titleService.setTitle(this.title);
  }
}
