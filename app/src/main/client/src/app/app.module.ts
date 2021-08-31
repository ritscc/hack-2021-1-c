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
import { UserEditCardComponent } from './components/presentational/user-edit-card/user-edit-card.component';
import { UsersNewComponent } from './components/page/admin/users-new/users-new.component';
import { UsersNewFormComponent } from './components/container/users-new-form/users-new-form.component';
import { UsersEditComponent } from './components/page/admin/users-edit/users-edit.component';
import { UsersEditFormComponent } from './components/container/users-edit-form/users-edit-form.component';
import { StampsComponent } from './components/page/stamps/stamps.component';
import { MypageComponent } from './components/page/mypage/mypage.component';
import { ProfileEditComponent } from './components/page/mypage/profile-edit/profile-edit.component';
import { PasswordEditComponent } from './components/page/mypage/password-edit/password-edit.component';
import { MypagePasswordEditFormComponent } from './components/container/mypage-password-edit-form/mypage-password-edit-form.component';
import { MypageProfileEditFormComponent } from './components/container/mypage-profile-edit-form/mypage-profile-edit-form.component';
import { StampsContentsComponent } from './components/container/stamps-contents/stamps-contents.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    TitleBoxComponent,
    ErrorPageComponent,
    LoginComponent,
    LoginFormComponent,
    LoginCardComponent,
    AdminComponent,
    UsersComponent,
    UsersContentsComponent,
    UsersTableComponent,
    UserEditCardComponent,
    UsersNewComponent,
    UsersNewFormComponent,
    UsersEditComponent,
    UsersEditFormComponent,
    StampsComponent,
    MypageComponent,
    ProfileEditComponent,
    PasswordEditComponent,
    MypagePasswordEditFormComponent,
    MypageProfileEditFormComponent,
    StampsContentsComponent,
  ],
  imports: [SharedModule, BrowserModule, AppRoutingModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
