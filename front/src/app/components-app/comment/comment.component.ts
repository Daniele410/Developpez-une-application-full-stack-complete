import { Component, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Comment } from 'src/app/interfaces/comment';
import { CommentService } from 'src/app/services/comment.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent implements OnInit {

  commentForm!: FormGroup;
  comments: Comment[] = [];
  postId: string = '';
  newComment!: Comment;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private commentService: CommentService,
    private postService: PostService

  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.postId = params['id']; // Access the 'id' parameter from the URL
      this.loadComments();
      this.initializeForm();
    }

    );
  }

  initializeForm(): void {
    this.commentForm = this.fb.group({
      comment: [''],
    });
  }

  loadComments(): void {
    this.commentService.getComments(this.postId).subscribe(
      (res) => {
        this.comments = res;
      },
      (error) => {
        console.error('Error loading comments:', error);
      }
    );
  }

  onSubmit() {
    if (this.commentForm.valid) {
      const commentData = {
        postId: this.postId,
        description: this.commentForm.value.comment
      };
      this.commentService.create(commentData.postId,commentData.description).subscribe(
        (response) => {
          console.log('Comment saved successfully:', response);
          this.loadComments();
          this.commentForm.reset(); // Reset the form after successful submission
        },
        (error) => {
          console.error('Error saving comment:', error);
        }
      );
    } else {
      if (this.commentForm.get('comment')?.invalid) {
        alert('Please enter a comment.');
      }
    }
  }

}
