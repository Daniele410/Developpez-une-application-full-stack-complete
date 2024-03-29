import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Topics } from '../../interfaces/topics.interface';
import { Observable, Subscription, forkJoin, map, switchMap, tap } from 'rxjs';
import { UserSessionService } from '../../services/user-session.service';
import { TopicsService } from '../../services/topics.service';
import { FormGroup } from '@angular/forms';
import { TopicService } from 'src/app/services/topic.service';
import { ActivatedRoute, Route, Router } from '@angular/router';

@Component({
  selector: 'app-topic-card',
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss']
})
export class TopicsCardComponent implements OnInit, OnDestroy {
  topicForm!: FormGroup;
  topics!: Topics[];
  topicId!: string;
  userSubscribed!: boolean;
  subscribedTopics: Topics[] = [];
  
  
  private destroy$: Subscription = new Subscription();
  constructor(
    private topicsService: TopicsService,
    private topicService: TopicService
  ) { }


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



 

  ngOnDestroy(): void {
    this.destroy$.unsubscribe();
  }

}


