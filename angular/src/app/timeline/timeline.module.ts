import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';

import { TimelineComponent } from './timeline.component';
@NgModule({
  declarations: [
    TimelineComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule
  ],
  exports: [
    TimelineComponent
  ]
})
export class TimelineModule { }
