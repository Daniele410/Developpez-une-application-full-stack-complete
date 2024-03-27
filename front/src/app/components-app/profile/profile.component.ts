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
    subscribedTopic: Topics[] = [];
    topics!: Topics[];
    topicId!: string;
    userSubscribed!: boolean;
   

  
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
        this.logout();
      });
    }

    ngOnInit(): void {
      this.getTopicsUserSubscribed().subscribe((topics) => {
        console.log(topics); // Add this line
        this.subscribedTopic = topics;

        this.getTopicsUserSubscribed().subscribe((topics) => {
          this.subscribedTopic = topics;

        });
      });
    }

    toggleSubscription(topic: any): void {

      this.topicService.subscribeToTopic(topic.id, !topic.isSubscribed).subscribe(response => {
        console.log(`Unsubscribed from topic ${topic.title}`);
        topic.isSubscribed = !topic.isSubscribed;
  
    
      });
    }

    subscribeToTopic() {
      const body = {
        topicId: this.topicId,
        isSubscribed: false
      };
      //utilizza il metodo subscribeToTopic del servizio per iscriversi o annullare l'iscrizione a un argomento
      if (this.userSubscribed) {
        this.topicService.subscribeToTopic(body.topicId, body.isSubscribed).subscribe(() => {
          this.userSubscribed = false; // Aggiorna lo stato di iscrizione dopo aver annullato l'iscrizione con successo
        });
      } else {
        this.topicService.subscribeToTopic(body.topicId, true).subscribe(() => {
          this.userSubscribed = true; // Aggiorna lo stato di iscrizione dopo aver sottoscritto con successo
        });
      }
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
