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

@NgModule({
  declarations: [AppComponent, DashboardComponent, TitleBoxComponent, ErrorPageComponent],
  imports: [SharedModule, BrowserModule, AppRoutingModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
