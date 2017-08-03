import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '../core/guards/auth.guard';
import { FarmersComponent } from './farmer-list/farmers.component';
import { FarmerDetailComponent } from './farmer-detail/farmer-detail.component';

const routes: Routes = [
  {
    path: 'farmers',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      { path: '', component: FarmersComponent },
      { path: 'register', component: FarmerDetailComponent },
      { path: ':id', component: FarmerDetailComponent }
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
export class FarmersRoutingModule { }
