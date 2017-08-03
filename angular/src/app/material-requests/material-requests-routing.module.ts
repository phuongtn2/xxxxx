import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '../core/guards/auth.guard';
import { MaterialRequestComponent } from './material-request/material-request.component';
import { MaterialRequestDetailComponent } from './material-request-detail/material-request-detail.component';

const routes: Routes = [
  {
    path: 'material-request',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      { path: '', component: MaterialRequestComponent },
      { path: ':id', component: MaterialRequestDetailComponent }
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
export class MaterialRequestsRoutingModule { }
