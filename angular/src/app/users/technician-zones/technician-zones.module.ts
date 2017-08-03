import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MdButtonModule,
  MdCardModule,
  MdCheckboxModule,
  MdIconModule,
  MdListModule,
  MdProgressSpinnerModule,
  MdTooltipModule
} from '@angular/material';

import { TechnicianZoneListComponent } from './technician-zone-list/technician-zone-list.component';
import { FlexLayoutModule } from '@angular/flex-layout';

@NgModule({
  declarations: [TechnicianZoneListComponent],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MdButtonModule,
    MdCardModule,
    MdCheckboxModule,
    MdIconModule,
    MdListModule,
    MdProgressSpinnerModule,
    MdTooltipModule
  ],
  exports: [
    TechnicianZoneListComponent
  ]
})
export class TechnicianZonesModule { }
