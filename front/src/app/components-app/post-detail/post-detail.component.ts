import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from 'src/app/interfaces/post';
import { CommentService } from 'src/app/services/comment.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss']
})
export class PostDetailComponent implements OnInit {

constructor(private route: ActivatedRoute, private postService:PostService, comments:CommentService ) { }

postId: string = '';
post!:Post;

  ngOnInit(): void {
    this.route.params.subscribe(params => {

      this.postId = params['id']; // Access the 'id' parameter from the URL
      
      console.log('Test ID:', this.postId);
      
      this.postService.detail(this.postId).subscribe((res) => { 
      this.post = res;


      console.log('Test Post:', this.post);
      
    
    }
    );



    });

    
  }



}
