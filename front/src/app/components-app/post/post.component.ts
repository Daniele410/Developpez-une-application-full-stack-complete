import { Component, OnInit } from '@angular/core';
import { Post } from '../../interfaces/post';
import { UserSessionService } from '../../services/user-session.service';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {
  posts:Post[] = [];

  constructor(private userSessionService:UserSessionService, private postService: PostService) { }


  ngOnInit(): void {
    this.postService.getPosts().subscribe((res) => { this.posts = res; });
  }
  

}
