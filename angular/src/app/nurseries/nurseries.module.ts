import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { LocationModule } from '../location/location.module';
import { TimelineModule } from '../timeline/timeline.module';
import { NurseriesRoutingModule } from './nurseries-routing.module';
import { CommonService } from '../core/services/common.service';
import { NurseryService } from './nursery.service';
import { NurseryActionDetailComponent } from './nursery-action-detail/nursery-action-detail.component';
import { NurseryCardComponent } from './nursery-card/nursery-card.component';
import { NurseryDetailComponent } from './nursery-detail/nursery-detail.component';
import { NurseryDetailDialogComponent } from './nursery-detail-dialog/nursery-detail-dialog.component';
import { NurseryTimelineComponent } from './nursery-timeline/nursery-timeline.component';

@NgModule({
  declarations: [
    NurseryActionDetailComponent,
    NurseryCardComponent,
    NurseryDetailComponent,
    NurseryDetailDialogComponent,
    NurseryTimelineComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    MdDataTableModule,
    ReactiveFormsModule,
    LocationModule,
    TimelineModule,
    NurseriesRoutingModule
  ],
  providers: [
    CommonService,
    NurseryService
  ],
  exports: [
    NurseryCardComponent
  ],
  entryComponents: [
    NurseryDetailDialogComponent
  ]
})
export class NurseriesModule { }
