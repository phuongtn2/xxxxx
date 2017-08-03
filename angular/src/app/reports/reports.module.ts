import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';

import { ReportsRoutingModule } from './reports-routing.module';
import { ActualAcreageReportComponent } from './actual-acreage-report/actual-acreage-report.component';
import { ExpectedOutputReportComponent } from './expected-output-report/expected-output-report.component';
import { FarmerCostReportComponent } from './farmer-cost-report/farmer-cost-report.component';
import { FarmerReportComponent } from './farmer-report/farmer-report.component';
import { NurseryReportComponent } from './nursery-report/nursery-report.component';
import { ObserverZoneReportComponent } from './observer-zone-report/observer-zone-report.component';
import { SeedReportComponent } from './seed-report/seed-report.component';
import { TaskMoveActionReportComponent } from './task-move-action-report/task-move-action-report.component';
import { TaskPlanReportComponent } from './task-plan-report/task-plan-report.component';
import { FertilizerReportComponent } from './fertilizer-report/fertilizer-report.component';
import { FromCharCodePipe } from '../core/pipes/from-char-code.pipe';

@NgModule({
  declarations: [
    ActualAcreageReportComponent,
    ExpectedOutputReportComponent,
    FarmerCostReportComponent,
    FarmerReportComponent,
    NurseryReportComponent,
    ObserverZoneReportComponent,
    SeedReportComponent,
    TaskMoveActionReportComponent,
    TaskPlanReportComponent,
    FertilizerReportComponent,
    FromCharCodePipe
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    ReactiveFormsModule,
    ReportsRoutingModule
  ]
})
export class ReportsModule { }
