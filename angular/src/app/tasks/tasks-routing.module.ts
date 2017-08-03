import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '../core/guards/auth.guard';
import { ScheduleComponent } from './schedule/schedule.component';
import { TaskDetailComponent } from './task-detail/task-detail.component';

const routes: Routes = [
  { path: 'schedule', component: ScheduleComponent, canActivate: [AuthGuard] },
  {
    path: 'user/:userId/schedule',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      { path: '', component: ScheduleComponent },
      { path: 'add', component: TaskDetailComponent },
      { path: ':taskId', component: TaskDetailComponent },
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
export class TasksRoutingModule { }
