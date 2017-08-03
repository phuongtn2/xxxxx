import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { FarmerRelativeService } from './farmer-relative.service';
import { FarmerRelativeListComponent } from './farmer-relative-list/farmer-relative-list.component';
import { FarmerRelativeRegisterDialogComponent } from './farmer-relative-register-dialog/farmer-relative-register-dialog.component';

@NgModule({
  declarations: [
    FarmerRelativeListComponent,
    FarmerRelativeRegisterDialogComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    MdDataTableModule,
    ReactiveFormsModule
  ],
  providers: [
    FarmerRelativeService
  ],
  entryComponents: [
    FarmerRelativeRegisterDialogComponent
  ],
  exports: [
    FarmerRelativeListComponent
  ]
})
export class FarmerRelativesModule { }
