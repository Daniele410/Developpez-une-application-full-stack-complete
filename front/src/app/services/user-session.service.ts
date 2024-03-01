import { Injectable } from '@angular/core';
import { User } from '../interfaces/user.interface';
import { BehaviorSubject, Observable } from 'rxjs';
import { Topics } from '../interfaces/topics.interface';

@Injectable({
  providedIn: 'root'
})
export class UserSessionService {
  $userInformation() {
    throw new Error('Method not implemented.');
  }
  
  public isLogged = false;
  public subscriptions: Topics[] = [];
  public user: User = {} as User;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  private subscriptionsSubject = new BehaviorSubject<Topics[]>(this.subscriptions);
  private userInformationSubject = new BehaviorSubject<User>(this.user);

  public setSubscriptions(subscriptions: Topics[]): void {
    this.subscriptions = subscriptions;
    this.subscriptions.sort((a, b) => a.title.localeCompare(b.title));
    this.subscriptionsSubject.next(this.subscriptions);
  }

  public setUserInformation(user: User): void {
    this.user = user;
    this.userInformationSubject.next(this.user);
  }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public $subscriptions(): Observable<Topics[]> {
    return this.subscriptionsSubject.asObservable();
  }

  public logIn(token: string): void {
    localStorage.setItem('token', token);
    this.isLogged = true;
    console.log('User logged in:', this.isLogged);
    this.next();
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this.setUserInformation({} as User);
    this.setSubscriptions([]);
    this.isLogged = false;
    console.log('User logout :', this.isLogged);
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
