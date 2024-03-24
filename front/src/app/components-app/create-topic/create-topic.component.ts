import { Component } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { TopicService } from "src/app/services/topic.service";


@Component({
  selector: 'app-create-topic',
  templateUrl: './create-topic.component.html',
  styleUrls: ['./create-topic.component.scss']
})
export class CreateTopicComponent {

  topicForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
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
          // Redirect to topics page
          // Replace 'topics' with the actual route path for the topics page
          this.router.navigate(['/topics']);
        },
        (error) => {
          console.error('Error saving post:', error);
          alert('An error occurred while saving the post.');
        }
      );
    } else {
      if (this.topicForm.get('title')?.invalid) {
        alert('Please enter a title.');
      }
    }
  }

  goBack() {
    window.history.back();
  }

}
