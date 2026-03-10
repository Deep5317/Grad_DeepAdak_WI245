import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './login/login';
import { Admin } from './admin/admin';
import { Staff } from './staff/staff';

const routes: Routes = [
  {
    path:'',
    component:Login
  },
  {
    path:'admin',
    component:Admin
  },
  {
    path:'staff',
    component:Staff
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
