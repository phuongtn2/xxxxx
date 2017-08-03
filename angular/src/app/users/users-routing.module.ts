import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '../core/guards/auth.guard';
import { UsersComponent } from './user-list/users.component';
import { CropUserRegisterComponent } from './crop-user-register/crop-user-register.component';

const routes: Routes = [
  {
    path: 'users',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      { path: '', component: UsersComponent },
      { path: 'register', component: CropUserRegisterComponent },
      { path: ':id', component: CropUserRegisterComponent }
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
export class UsersRoutingModule { }
