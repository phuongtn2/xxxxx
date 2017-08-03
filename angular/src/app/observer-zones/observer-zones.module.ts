import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import {
  MdButtonModule, MdCardModule, MdDialogModule, MdIconModule, MdListModule,
  MdSelectModule
} from '@angular/material';

import { ObserverService } from '../core/services/observer.service';
import { ObserverZoneDialogComponent } from './observer-zone-dialog/observer-zone-dialog.component';
import { ObserverZoneListComponent } from './observer-zone-list/observer-zone-list.component';

@NgModule({
  declarations: [
    ObserverZoneDialogComponent,
    ObserverZoneListComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MdButtonModule,
    MdCardModule,
    MdDialogModule,
    MdIconModule,
    MdListModule,
    MdSelectModule,
    ReactiveFormsModule
  ],
  providers: [
    ObserverService
  ],
  exports: [
    ObserverZoneListComponent
  ],
  entryComponents: [
    ObserverZoneDialogComponent
  ]
})
export class ObserverZonesModule { }
