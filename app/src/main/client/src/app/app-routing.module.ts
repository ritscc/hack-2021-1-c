import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// dashboard
import { DashboardComponent } from './components/page/dashboard/dashboard.component';
// others
import { ErrorPageComponent } from './components/page/error-page/error-page.component';
import { HeaderComponent } from './shared/components/header/header.component';

// guards
import { AuthGuard } from './shared/guards/auth.guard';
import { LoginedGuard } from './shared/guards/logined.guard';
import { AdminGuard } from './shared/guards/admin.guard';

const routes: Routes = [
  {
    path: '',
    component: HeaderComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', component: DashboardComponent },
      {
        path: 'dashboard',
        component: DashboardComponent,
      },
    ],
  },
  { path: 'error', component: ErrorPageComponent },
  { path: '**', redirectTo: '/error?status_code=404', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
