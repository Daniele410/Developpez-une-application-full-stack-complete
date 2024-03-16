import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Topics } from '../../interfaces/topics.interface';
import { Subscription } from 'rxjs';
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
  topicForm!:FormGroup;
  topics!: Topics[];
  topicId!: string;
  userSubscribed!: boolean;
  
  

 private destroy$: Subscription = new Subscription();
 constructor(private userSessionService: UserSessionService,
   private topicsService: TopicsService,
    private topicService:TopicService,
    private route: ActivatedRoute,
    ) {}

 
 ngOnInit(): void {
  
   this.topicsService.getTopics().subscribe((res) => { this.topics = res; }
   );
   this.route.params.subscribe(params => {
    this.topicId = params['id'];
   });
//  this.userSessionService.$subscriptions().subscribe((subscriptions) => {
//      this.userSubscribed = subscriptions.some((subscription) => subscription.id === this.topics[0].id);
//    });

  //  this.checkSubscriptionStatus(); // Controlla lo stato dell'iscrizione quando il componente viene caricato
   
  //  this.route.params.subscribe(params => {
  //    this.topicId = params['id']; // Access the 'id' parameter from the URL
  //  });
  this.topicsService.getTopics().subscribe((res) => { 
    this.topics = res; 
    this.topics.forEach(topic => {
      this.route.params.subscribe(params => {
        this.topicId = params['id'];
        console.log(`Topic ID for ${topic.title}: ${this.topicId}`);
      });
    });
  });
 }

toggleSubscription(topic: any): void {
  this.userSubscribed = !this.userSubscribed;

  if (this.userSubscribed) {
    // If the user is subscribing, call the subscribe method of the service
    this.topicService.subscribeToTopic(topic.id, true).subscribe(response => {
      console.log(`Subscribed to topic ${topic.title}`);
    });
  } else {
    // If the user is unsubscribing, call the unsubscribe method of the service
    this.topicService.subscribeToTopic(topic.id, false).subscribe(response => {
      console.log(`Unsubscribed from topic ${topic.title}`);
    });
  }
}


checkSubscriptionStatus(): void {
  // Aggiorna il valore di userSubscribed in base allo stato di iscrizione
  // Puoi chiamare il servizio per ottenere lo stato di iscrizione dal backend
  // Esempio:
  // this.topicService.checkSubscriptionStatus(this.topicId).subscribe((isSubscribed: boolean) => {
  //   this.userSubscribed = isSubscribed;
  // });
}

subscribeToTopic() {
  const body = {
    topicId: this.topicId,
    isSubscribed: false
  };
//utilizza il metodo subscribeToTopic del servizio per iscriversi o annullare l'iscrizione a un argomento
  if (this.userSubscribed) {
    this.topicService.subscribeToTopic(body.topicId,body.isSubscribed).subscribe(() => {
      this.userSubscribed = false; // Aggiorna lo stato di iscrizione dopo aver annullato l'iscrizione con successo
    });
  } else {
    this.topicService.subscribeToTopic(body.topicId, true).subscribe(() => {
      this.userSubscribed = true; // Aggiorna lo stato di iscrizione dopo aver sottoscritto con successo
    });
  }
}

ngOnDestroy(): void {
  this.destroy$.unsubscribe();
}

}


