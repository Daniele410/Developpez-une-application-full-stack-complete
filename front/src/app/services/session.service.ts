import { BehaviorSubject, Observable } from 'rxjs';
import { AuthService } from '../features/auth/services/auth.service';
import { User } from '../interfaces/user.interface';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor(private router: Router) {}
  public user: User | undefined;

  public logIn(user: User, token: string): void {
    this.user = user;
    sessionStorage.setItem('token', token);
  }

  public logOut(): void {
    sessionStorage.removeItem('token');
    console.log(sessionStorage.getItem('token'))
    this.user = undefined;
    this.router.navigate(['/login']);
  }

  public isLogged(): boolean {
    return sessionStorage.getItem('token') !== null && sessionStorage.getItem('token') !== undefined;
  }


}
