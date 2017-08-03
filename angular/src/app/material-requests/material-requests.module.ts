import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { MaterialRequestsRoutingModule } from './material-requests-routing.module';
import { MaterialRequestComponent } from './material-request/material-request.component';
import { MaterialRequestDetailComponent } from './material-request-detail/material-request-detail.component';

@NgModule({
  declarations: [
    MaterialRequestComponent,
    MaterialRequestDetailComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    FormsModule,
    MaterialModule,
    MdDataTableModule,
    MaterialRequestsRoutingModule
  ]
})
export class MaterialRequestsModule { }
