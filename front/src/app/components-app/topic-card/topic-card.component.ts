import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Topics } from '../../interfaces/topics.interface';
import { Subscription } from 'rxjs';
import { UserSessionService } from '../../services/user-session.service';
import { TopicsService } from '../../services/topics.service';

@Component({
  selector: 'app-topic-card',
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss']
})
export class TopicsCardComponent implements OnInit, OnDestroy {
   topics!: Topics[];
  public userSubscribed: boolean = false;

  private destroy$: Subscription = new Subscription();
  constructor(private userSessionService: UserSessionService, private topicsService: TopicsService) {}

  // public toggleSubscription(): void {
  //   if (this.userSubscribed) {
  //     this.unsubscribe();
  //   } else {
  //     this.subscribe();
  //   }

  //   this.userSubscribed = !this.userSubscribed;
  // }

  // private unsubscribe(): void {
  //   this.topicsService.unsubscribeFromTopics(this.topics.id).subscribe({
  //     next: () => {
  //       this.userSessionService.setSubscriptions(
  //         this.userSessionService.subscriptions.filter((subscription) => subscription.id !== this.topics.id)
  //       );
  //     },
  //   });
  // }

  // private subscribe(): void {
  //   this.topicsService.subscribeToTopic(this.topics.id).subscribe({
  //     next: () => {
  //       this.userSessionService.setSubscriptions([...this.userSessionService.subscriptions, this.topics]);
  //     },
  //   });
  // }

  ngOnInit(): void {
    // this.userSessionService.$subscriptions().subscribe((subscriptions) => {
    //   this.userSubscribed = subscriptions.some((subscription) => subscription.id === this.topics.id);
    // });
    this.topicsService.getTopics().subscribe((res) => { this.topics = res; }
    );
  }

  ngOnDestroy(): void {
    this.destroy$.unsubscribe();
  }
}
