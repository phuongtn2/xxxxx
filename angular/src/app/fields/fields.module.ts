import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { LocationModule } from '../location/location.module';
import { TimelineModule } from '../timeline/timeline.module';
import { FieldsRoutingModule } from './fields-routing.module';
import { FieldService } from './field.service';
import { FieldCardComponent } from './field-card/field-card.component';
import { FieldDetailComponent } from './field-detail/field-detail.component';
import { FieldDetailDialogComponent } from './field-detail-dialog/field-detail-dialog.component';
import { PlotDetailComponent } from './plot-detail/plot-detail.component';
import { PlotActionDetailComponent } from './plot-action-detail/plot-action-detail.component';
import { FieldPlotTimelineComponent } from './field-plot-timeline/field-plot-timeline.component';

@NgModule({
  declarations: [
    FieldCardComponent,
    FieldDetailComponent,
    FieldDetailDialogComponent,
    PlotDetailComponent,
    PlotActionDetailComponent,
    FieldPlotTimelineComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    MdDataTableModule,
    ReactiveFormsModule,
    LocationModule,
    TimelineModule,
    FieldsRoutingModule
  ],
  providers: [
    FieldService
  ],
  exports: [
    FieldCardComponent
  ],
  entryComponents: [
    FieldDetailDialogComponent
  ]
})
export class FieldsModule { }
