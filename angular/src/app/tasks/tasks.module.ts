import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule, MdNativeDateModule } from '@angular/material';

import { AppCalendarModule } from '../app-calendar/app-calendar.module';
import { ScheduleComponent } from './schedule/schedule.component';
import { TaskDetailComponent } from './task-detail/task-detail.component';
import { TaskDetailDialogComponent } from './task-detail-dialog/task-detail-dialog.component';
import { TaskListComponent } from './task-list/task-list.component';
import { TasksRoutingModule } from './tasks-routing.module';
import { LocationModule } from '../location/location.module';

@NgModule({
  declarations: [
    ScheduleComponent,
    TaskDetailComponent,
    TaskDetailDialogComponent,
    TaskListComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    FormsModule,
    MaterialModule,
    MdNativeDateModule,
    ReactiveFormsModule,
    AppCalendarModule,
    TasksRoutingModule,
    LocationModule
  ],
  entryComponents: [
    TaskDetailDialogComponent
  ]
})
export class TasksModule { }
