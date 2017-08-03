import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { FieldsModule } from '../fields/fields.module';
import { NurseriesModule } from '../nurseries/nurseries.module';
import { CropSessionRoutingModule } from './crop-session-routing.module';
import { CropMaterialListComponent } from './crop-material-list/crop-material-list.component';
import { DryingOvenListComponent } from './drying-oven-list/drying-oven-list.component';
import { RegistrationDetailComponent } from './registration-detail/registration-detail.component';
import { RegistrationListComponent } from './registration-list/registration-list.component';
import { SaleListComponent } from './sale-list/sale-list.component';
import { LocationModule } from '../location/location.module';

@NgModule({
  declarations: [
    CropMaterialListComponent,
    DryingOvenListComponent,
    RegistrationDetailComponent,
    RegistrationListComponent,
    SaleListComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    MdDataTableModule,
    ReactiveFormsModule,
    CropSessionRoutingModule,
    FieldsModule,
    LocationModule,
    NurseriesModule
  ]
})
export class CropSessionModule { }
