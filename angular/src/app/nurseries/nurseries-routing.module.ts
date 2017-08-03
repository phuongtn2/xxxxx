import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '../core/guards/auth.guard';
import { NurseryDetailComponent } from './nursery-detail/nursery-detail.component';
import { NurseryActionDetailComponent } from './nursery-action-detail/nursery-action-detail.component';
import { NurseryTimelineComponent } from './nursery-timeline/nursery-timeline.component';

const routes: Routes = [
  {
    path: 'crop-session/farmer/:farmerCode/nursery-detail',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      { path: '', component: NurseryDetailComponent },
      { path: 'action/:actionId', component: NurseryActionDetailComponent },
      { path: 'timeline', component: NurseryTimelineComponent }
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
export class NurseriesRoutingModule { }
