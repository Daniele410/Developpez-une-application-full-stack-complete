import { Component, OnInit } from '@angular/core';
import { Post } from '../../interfaces/post';
import { UserSessionService } from '../../services/user-session.service';
import { PostService } from '../../services/post.service';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { Topics } from 'src/app/interfaces/topics.interface';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {
  sortByNewestFirst: boolean = true;
  posts: Post[] = [];
  myTopics: Topics[] = [];

  constructor(private userSessionService: UserSessionService, private postService: PostService, private router: Router) { }


  ngOnInit(): void {
    this.postService.getPosts().subscribe((res) => { this.posts = res; });

  }



  goToDetail(post: Post) {

    this.router.navigate(['/posts', post.id]);


  }

  sortByDate() {
    if (this.sortByNewestFirst) {
        this.posts.sort((a, b) => {
            return <any>new Date(b.createdAt) - <any>new Date(a.createdAt);
        });
    } else {
        this.posts.sort((a, b) => {
            return <any>new Date(a.createdAt) - <any>new Date(b.createdAt);
        });
    }
    // Inverti il valore di sortByNewestFirst a ogni chiamata
    this.sortByNewestFirst = !this.sortByNewestFirst;
}



}
