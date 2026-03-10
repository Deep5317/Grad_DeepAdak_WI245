import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Admin } from './admin/admin';
import { Staff } from './staff/staff';
import { Login } from './login/login';
import { StudentForm } from './student-form/student-form';
import { StudentList } from './student-list/student-list';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [App, Admin, Staff, Login, StudentForm, StudentList],
  imports: [BrowserModule, AppRoutingModule, FormsModule],
  providers: [provideBrowserGlobalErrorListeners()],
  bootstrap: [App],
})
export class AppModule {}
