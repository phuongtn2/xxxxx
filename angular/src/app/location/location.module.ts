import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MdSelectModule } from '@angular/material';
import { NguiMapModule } from '@ngui/map';

import { LocationComponent } from './location/location.component';
import { MapDialogComponent } from './map-dialog/map-dialog.component';
import { MiniMapComponent } from './mini-map/mini-map.component';

@NgModule({
  declarations: [
    LocationComponent,
    MapDialogComponent,
    MiniMapComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MdSelectModule,
    NguiMapModule.forRoot({apiUrl: 'https://maps.googleapis.com/maps/api/js?key=AIzaSyCqHIi6q353jJ_e7fg_ZIhQuodTC3f_bs0'}),
    ReactiveFormsModule
  ],
  exports: [
    LocationComponent,
    MapDialogComponent,
    MiniMapComponent
  ],
  entryComponents: [
    MapDialogComponent
  ]
})
export class LocationModule { }
