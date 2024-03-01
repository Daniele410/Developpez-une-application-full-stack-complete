import { Component, NgModule, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostService } from '../../services/post.service';
import { TopicsService } from '../../services/topics.service';
import { Topics } from '../../interfaces/topics.interface';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from '@angular/material/select'; 


@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss'],
  
})

export class CreatePostComponent implements OnInit {
  postForm!: FormGroup;
  topics!: Topics[];

  constructor(
    private fb: FormBuilder,
    private postService: PostService,
    private topicsService: TopicsService
  ) {}

  ngOnInit(): void {
    this.initializeForm();
    this.loadTopics();
  }

  initializeForm(): void {
    this.postForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      topics: [''],
    });
  }

  loadTopics(): void {
    this.topicsService.getTopics().subscribe(
      (res) => {
        this.topics = res;
      },
      (error) => {
        console.error('Error loading topics:', error);
      }
    );
  }

  onSubmit(): void {
    if (this.postForm.valid) {
      const postData = this.postForm.value;
      this.postService.create(postData).subscribe(
        (response) => {
          console.log('Post saved successfully:', response);
        },
        (error) => {
          console.error('Error saving post:', error);
        }
      );
    } else {
      if (this.postForm.get('title')?.invalid) {
        alert('Please enter a title.');
      }
    }
  }
}