import { BehaviorSubject, Observable } from 'rxjs';
import { AuthService } from '../features/auth/services/auth.service';
import { User } from '../interfaces/user.interface';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {


  public user: User | undefined;

  public logIn(user: User): void {
    this.user = user;
  }

  public logOut(): void {
    sessionStorage.removeItem('token');
    this.user = undefined;
  }

  public isLogged(): boolean {
    return sessionStorage.getItem('token') !== null && sessionStorage.getItem('token') !== undefined;
  }


}
