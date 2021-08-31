import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// shared module
import { SharedModule } from './shared/shared.module';

// components
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './components/page/dashboard/dashboard.component';
import { TitleBoxComponent } from './components/presentational/title-box/title-box.component';
import { ErrorPageComponent } from './components/page/error-page/error-page.component';
import { LoginComponent } from './components/page/login/login.component';
import { LoginFormComponent } from './components/container/login-form/login-form.component';
import { LoginCardComponent } from './components/presentational/login-card/login-card.component';
import { AdminComponent } from './components/page/admin/admin.component';
import { UsersComponent } from './components/page/admin/users/users.component';
import { UsersContentsComponent } from './components/container/users-contents/users-contents.component';
import { UsersTableComponent } from './components/presentational/users-table/users-table.component';

@NgModule({
  declarations: [AppComponent, DashboardComponent, TitleBoxComponent, ErrorPageComponent, LoginComponent, LoginFormComponent, LoginCardComponent, AdminComponent, UsersComponent, UsersContentsComponent, UsersTableComponent],
  imports: [SharedModule, BrowserModule, AppRoutingModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
