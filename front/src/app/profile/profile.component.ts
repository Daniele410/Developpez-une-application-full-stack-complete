import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Title } from '@angular/platform-browser';
import { UserSessionService } from '../user-session.service';
import { UserService } from '../services/user.service';
import { TopicsService } from '../topics.service';
import { Topics } from '../interfaces/topics.interface';



@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})



  export class ProfileComponent implements OnInit, OnDestroy {
    public user: any = this.userSessionService.user;
    public updateValid: boolean = false;
    name = new FormControl('');
  
    constructor(
      private userSessionService: UserSessionService,
      private userService: UserService,
      private topicsService: TopicsService,
      private router: Router,
      private fb: FormBuilder,
      private title: Title
    ) {
      this.title.setTitle('MDD - Profile');
    }
  
    public form = this.fb.group({
      username: [this.user.username, [Validators.required, Validators.minLength(3)]],
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
        }, 3000);
      });
    }
  
    logout() {
      this.userSessionService.logOut();
      this.router.navigate(['/']);
    }
  
    ngOnInit(): void {
      this.topicsService.getUserSubscribedTopics().subscribe((res) => this.userSessionService.setSubscriptions(res));
      this.destroy$ = this.userSessionService.$subscriptions().subscribe((subscriptions) => {
        this.topicsSubcriptions = subscriptions;
      });
    }
  
    ngOnDestroy(): void {
      this.destroy$.unsubscribe();
    }

}
