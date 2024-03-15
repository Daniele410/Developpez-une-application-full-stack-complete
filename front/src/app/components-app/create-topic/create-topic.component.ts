import { Component } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { TopicService } from "src/app/services/topic.service";


@Component({
  selector: 'app-create-topic',
  templateUrl: './create-topic.component.html',
  styleUrls: ['./create-topic.component.scss']
})
export class CreateTopicComponent {

  topicForm: FormGroup;

  constructor(
    private fb:FormBuilder, 
    private topicService: TopicService) {
    this.topicForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
   }

onSubmit(): void {
  if (this.topicForm.valid) {
    const topicData = this.topicForm.value;
    this.topicService.create(topicData).subscribe(
      (response) => {
        console.log('Post saved successfully:', response);
      },
      (error) => {
        console.error('Error saving post:', error);
      }
    );
  } else {
    if (this.topicForm.get('title')?.invalid) {
      alert('Please enter a title.');
    }
  }
}

}
