import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { JobRecommendationsComponent } from './job-recommendations/job-recommendations.component';
import { AuthGuard } from './services/auth.guard';
import { AuthRedirectGuard } from './services/auth-redirect.guard';
import {JobManagementComponent} from './job-management/job-management.component';
import {RoleGuard} from './services/role.guard';


export const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthRedirectGuard] },
  { path: 'login', component: LoginComponent, canActivate: [AuthRedirectGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [AuthRedirectGuard] },
  { path: 'recommendations', component: JobRecommendationsComponent, canActivate: [AuthGuard] },
  { path: 'job-management', component: JobManagementComponent, canActivate: [RoleGuard] },
  { path: '**', redirectTo: '' }, // Redirect unknown paths to Home page
];
