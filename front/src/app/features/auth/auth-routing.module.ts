import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { NgModule } from '@angular/core';
import { HomeComponent } from 'src/app/pages/home/home.component';


const routes: Routes = [
  { title: '', path: '', component: HomeComponent },
  { title: 'login', path: 'login', component: LoginComponent },
  { title: 'register', path: 'register', component: RegisterComponent },
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
