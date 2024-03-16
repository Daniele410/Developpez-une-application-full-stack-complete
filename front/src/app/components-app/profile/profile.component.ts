import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Title } from '@angular/platform-browser';
import { UserSessionService } from '../../services/user-session.service';
import { UserService } from '../../services/user.service';
import { TopicsService } from '../../services/topics.service';
import { Topics } from '../../interfaces/topics.interface';
import { User } from 'src/app/interfaces/user.interface';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})

  export class ProfileComponent implements OnInit, OnDestroy {
    public user: User = this.userSessionService.user;
    public updateValid: boolean = false;
    name = new FormControl('');
  
    constructor(
      private userSessionService: UserSessionService,
      private userService: UserService,
      private topicsService: TopicsService,
      private router: Router,
      private fb: FormBuilder,
      private title: Title,
      private sessionService: UserSessionService
    ) {
      this.title.setTitle('MDD - Profile');
    }
  
    public form = this.fb.group({
      name: [this.user.name, [Validators.required, Validators.minLength(2)]],
      email: [this.user.email, [Validators.required, Validators.email]],
    });
  
    public topicsSubcriptions: Topics[] = [];
    private destroy$: Subscription = new Subscription();
  
    submit() {
      const request = this.form.value;
      this.userService.updateMe(request).subscribe((res) => {
        this.userSessionService.setUserInformation(res);
        this.updateValid = true;
        setTimeout(() => {
          this.updateValid = false;
        }, 3500);
      });
    }
  
    
  
    ngOnInit(): void {
      this.topicsService.getUserSubscribedTopics().subscribe((res) => this.userSessionService.setSubscriptions(res));
      this.destroy$ = this.userSessionService.$subscriptions().subscribe((subscriptions) => {
        this.topicsSubcriptions = subscriptions;
      });
    }

    logout() {
      this.sessionService.logout();
      this.router.navigate(['/login']);
      sessionStorage.removeItem('token');
      console.log(sessionStorage.getItem('token'));
    }
  
    ngOnDestroy(): void {
      this.destroy$.unsubscribe();
    }

}
