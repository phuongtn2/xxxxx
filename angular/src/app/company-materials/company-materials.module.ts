import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { CompanyMaterialsRoutingModule } from './company-materials-routing.module';
import { MaterialDetailDialogComponent } from './material-detail-dialog/material-detail-dialog.component';
import { MaterialsComponent } from './material-list/materials.component';
import { FlexLayoutModule } from '@angular/flex-layout';

@NgModule({
  declarations: [
    MaterialDetailDialogComponent,
    MaterialsComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    MdDataTableModule,
    ReactiveFormsModule,
    CompanyMaterialsRoutingModule
  ],
  entryComponents: [
    MaterialDetailDialogComponent
  ]
})
export class CompanyMaterialsModule { }
