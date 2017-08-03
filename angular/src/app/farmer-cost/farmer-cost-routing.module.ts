import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '../core/guards/auth.guard';
import { FarmerCostComponent } from './farmer-cost/farmer-cost.component';

const routes: Routes = [
  { path: 'farmer-cost', component: FarmerCostComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class FarmerCostRoutingModule { }
