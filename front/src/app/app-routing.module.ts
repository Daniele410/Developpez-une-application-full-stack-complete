import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/auth/components/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { NgModule } from '@angular/core';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';
import { ProfileComponent } from './components-app/profile/profile.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { TopicComponent } from './components-app/topic/topic.component';
import { RegisterComponent } from './features/auth/components/register/register.component';
import { PostComponent } from './components-app/post/post.component';
import { CreateTopicComponent } from './components-app/create-topic/create-topic.component';
import {  CreatePostComponent } from './components-app/create-post/create-post.component';
import { PostDetailComponent } from './components-app/post-detail/post-detail.component';

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
  { path: 'posts',
  canActivate: [AuthGuard], component: PostComponent },

  { path: 'posts/:id',
  canActivate: [AuthGuard], component: PostDetailComponent},

  { path: 'create-post',
  canActivate: [AuthGuard], component: CreatePostComponent },
  { path: 'create-topic',
  canActivate: [AuthGuard], component: CreateTopicComponent },
  { path: 'profile',
  canActivate: [AuthGuard], component: ProfileComponent },

  { path: '404', canActivate: [UnauthGuard], component: NotFoundComponent },

  { path: '**', redirectTo: '404' }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
