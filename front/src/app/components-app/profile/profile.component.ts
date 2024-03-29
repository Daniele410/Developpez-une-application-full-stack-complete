import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { Title } from '@angular/platform-browser';
import { UserSessionService } from '../../services/user-session.service';
import { UserService } from '../../services/user.service';
import { TopicsService } from '../../services/topics.service';
import { Topics } from '../../interfaces/topics.interface';
import { User } from 'src/app/interfaces/user.interface';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})

export class ProfileComponent implements OnInit, OnDestroy {
  public user: User = this.userSessionService.user;
  public updateValid: boolean = false;
  name = new FormControl('');
  subscribedTopics: Topics[] = [];
  topics!: Topics[];
  topicId!: string;
  userSubscribed!: boolean;
  public topicsSubcriptions: Topics[] = [];
  private destroy$: Subscription = new Subscription();
  
  constructor(
    private userSessionService: UserSessionService,
    private userService: UserService,
    private topicsService: TopicsService,
    private topicService: TopicService,
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



  submit() {
    const request = this.form.value;
    this.userService.updateMe(request).subscribe((res) => {
      this.userSessionService.setUserInformation(res);
      this.updateValid = true;
      setTimeout(() => {
        this.updateValid = false;
      }, 3500);
      this.logout();
    });
  }

  ngOnInit(): void {
    this.topicsService.getTopics().subscribe((res) => {
      this.topics = res;

      this.getTopicsUserSubscribed().subscribe((topics) => {
        this.subscribedTopics = topics;
      });
    });
  }


  toggleSubscription(topic: any): void {
    this.topicService.subscribeToTopic(topic.id, !topic.isSubscribed).subscribe(response => {
        console.log(`Unsubscribed from topic ${topic.title}`);
        topic.isSubscribed = !topic.isSubscribed; 
    });
}


  getTopicsUserSubscribed(): Observable<Topics[]> {
    return this.topicsService.getUserSubscribedTopics();
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
