import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { FarmerCostRoutingModule } from './farmer-cost-routing.module';
import { FarmerCostComponent } from './farmer-cost/farmer-cost.component';
import { FarmerCostDetailDialogComponent } from './farmer-cost-detail-dialog/farmer-cost-detail-dialog.component';
import { FarmerCostService } from './farmer-cost.service';

@NgModule({
  declarations: [
    FarmerCostComponent,
    FarmerCostDetailDialogComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    FormsModule,
    MaterialModule,
    MdDataTableModule,
    ReactiveFormsModule,
    FarmerCostRoutingModule
  ],
  providers: [
    FarmerCostService
  ],
  entryComponents: [
    FarmerCostDetailDialogComponent
  ]
})
export class FarmerCostModule { }
