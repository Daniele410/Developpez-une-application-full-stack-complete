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
 constructor(private userSessionService: UserSessionService, private topicsService: TopicsService, private topicService:TopicService, private route: ActivatedRoute) {}

 
 ngOnInit(): void {
  //  this.userSessionService.$subscriptions().subscribe((subscriptions) => {
  //    this.userSubscribed = subscriptions.some((subscription) => subscription.id === this.topics[0].id);
  //  });
   this.topicsService.getTopics().subscribe((res) => { this.topics = res; }
   );

  //  this.checkSubscriptionStatus(); // Controlla lo stato dell'iscrizione quando il componente viene caricato
   
  //  this.route.params.subscribe(params => {
  //    this.topicId = params['id']; // Access the 'id' parameter from the URL
  //  });
 }

 ngOnDestroy(): void {
  this.destroy$.unsubscribe();
}

 toggleSubscription(): void {
  this.userSubscribed = !this.userSubscribed;
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
  if (this.topicForm.valid) {
    const topicData = {
      topicId: this.topicId,
      userSubscribed: this.topicForm.value.topic
    };
//utilizza il metodo subscribeToTopic del servizio per iscriversi o annullare l'iscrizione a un argomento

  if (this.userSubscribed) {
    this.topicService.subscribeToTopic(this.topicId, false).subscribe(() => {
      this.userSubscribed = false; // Aggiorna lo stato di iscrizione dopo aver annullato l'iscrizione con successo
    });
  } else {
    this.topicService.subscribeToTopic(this.topicId, true).subscribe(() => {
      this.userSubscribed = true; // Aggiorna lo stato di iscrizione dopo aver sottoscritto con successo
    });
  }
}
}
}

