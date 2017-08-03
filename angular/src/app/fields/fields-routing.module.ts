import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '../core/guards/auth.guard';
import { FieldDetailComponent } from './field-detail/field-detail.component';
import { PlotDetailComponent } from './plot-detail/plot-detail.component';
import { PlotActionDetailComponent } from './plot-action-detail/plot-action-detail.component';
import { FieldPlotTimelineComponent } from './field-plot-timeline/field-plot-timeline.component';

const routes: Routes = [
  {
    path: 'crop-session/farmer/:farmerCode/field-detail',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      { path: '', component: FieldDetailComponent },
      {
        path: 'plot/:plotId',
        children: [
          { path: '', component: PlotDetailComponent },
          { path: 'action/:actionId', component: PlotActionDetailComponent },
          { path: 'timeline', component: FieldPlotTimelineComponent }
        ]
      }
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
export class FieldsRoutingModule { }
