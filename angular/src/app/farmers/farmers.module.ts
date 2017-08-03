import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { FarmerDetailComponent } from './farmer-detail/farmer-detail.component';
import { FarmerRegisterDialogComponent } from './farmer-register-dialog/farmer-register-dialog.component';
import { FarmersComponent } from './farmer-list/farmers.component';
import { FarmersRoutingModule } from './farmers-routing.module';
import { FarmerRelativesModule } from '../farmer-relatives/farmer-relatives.module';
import { LocationModule } from '../location/location.module';

@NgModule({
  declarations: [
    FarmerDetailComponent,
    FarmerRegisterDialogComponent,
    FarmersComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    MdDataTableModule,
    ReactiveFormsModule,
    FarmerRelativesModule,
    LocationModule,
    FarmersRoutingModule
  ],
  entryComponents: [
    FarmerRegisterDialogComponent
  ]
})
export class FarmersModule { }
