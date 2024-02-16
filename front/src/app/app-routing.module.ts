import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/auth/components/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { NgModule } from '@angular/core';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';
import { ProfileComponent } from './profile/profile.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { TopicComponent } from './topic/topic.component';
import { RegisterComponent } from './features/auth/components/register/register.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '',
  canActivate: [UnauthGuard], component: HomeComponent },
  { path: 'login',
  canActivate: [UnauthGuard], component: LoginComponent },
  { path: 'register',
  canActivate: [UnauthGuard], component: RegisterComponent },
  { path: 'topics',
  canActivate: [AuthGuard], component: TopicComponent },
  { path: 'profile',
  canActivate: [AuthGuard], component: ProfileComponent },

  { path: '404', component: NotFoundComponent },

  { path: '**', redirectTo: '404' }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
