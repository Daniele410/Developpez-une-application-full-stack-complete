import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
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

  constructor(
    private fb: FormBuilder,
    private commentService: CommentService,
    private postService: PostService
  ) {}

  ngOnInit(): void {
  }

}
