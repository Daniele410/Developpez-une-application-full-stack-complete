import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TopicService } from '../../services/topic.service';

@Component({
  selector: 'app-create-topic',
  templateUrl: './create-topic.component.html',
  styleUrls: ['./create-topic.component.scss']
})
export class CreateTopicComponent {

  topicForm: FormGroup;

  constructor(private fb:FormBuilder, 
    private topicService: TopicService) {
    this.topicForm = this.fb.group({
      name: [''],
      title: [''],
      description: ['']
    });
   }

  onSubmit() {
    const topicData = this.topicForm.value;
    this.topicService.create(topicData).subscribe(response => {
    console.log('Topic saved successfully', response);
  }, error => {
    console.error('Error saving topic:', error);
  });
  }
}
