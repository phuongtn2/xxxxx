import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '../core/guards/auth.guard';
import { ActualAcreageReportComponent } from './actual-acreage-report/actual-acreage-report.component';
import { ExpectedOutputReportComponent } from './expected-output-report/expected-output-report.component';
import { FarmerCostReportComponent } from './farmer-cost-report/farmer-cost-report.component';
import { FarmerReportComponent } from './farmer-report/farmer-report.component';
import { FertilizerReportComponent } from './fertilizer-report/fertilizer-report.component';
import { NurseryReportComponent } from './nursery-report/nursery-report.component';
import { ObserverZoneReportComponent } from './observer-zone-report/observer-zone-report.component';
import { SeedReportComponent } from './seed-report/seed-report.component';
import { TaskMoveActionReportComponent } from './task-move-action-report/task-move-action-report.component';
import { TaskPlanReportComponent } from './task-plan-report/task-plan-report.component';

const routes: Routes = [
  {
    path: 'report',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      { path: 'farmer', component: FarmerReportComponent },
      { path: 'observer-zone', component: ObserverZoneReportComponent },
      { path: 'nursery', component: NurseryReportComponent },
      { path: 'seed', component: SeedReportComponent },
      { path: 'actual-acreage', component: ActualAcreageReportComponent },
      { path: 'fertilizer', component: FertilizerReportComponent },
      { path: 'expected-output', component: ExpectedOutputReportComponent },
      { path: 'farmer-cost', component: FarmerCostReportComponent },
      { path: 'task-plan', component: TaskPlanReportComponent },
      { path: 'task-detail', component: TaskMoveActionReportComponent }
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class ReportsRoutingModule { }
