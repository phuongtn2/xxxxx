import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { LocationModule } from '../location/location.module';
import { ObserverZonesModule } from '../observer-zones/observer-zones.module';
import { TechnicianZonesModule } from './technician-zones/technician-zones.module';
import { UsersRoutingModule } from './users-routing.module';
import { CropUserRegisterComponent } from './crop-user-register/crop-user-register.component';
import { UsersComponent } from './user-list/users.component';

@NgModule({
  declarations: [
    CropUserRegisterComponent,
    UsersComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    MdDataTableModule,
    ReactiveFormsModule,
    LocationModule,
    ObserverZonesModule,
    TechnicianZonesModule,
    UsersRoutingModule
  ]
})
export class UsersModule { }
