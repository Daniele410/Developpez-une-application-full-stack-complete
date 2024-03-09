import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private pathService = "http://localhost:8080/api";

  constructor(private httpClient: HttpClient) { }

  public getComments(postId: string){
    return this.httpClient.get(this.pathService +'/posts/'+postId+'/comments');
  }

  public create(postId: string, comment: string){
    return this.httpClient.post(this.pathService +'/posts/'+postId+'/comments', {content: comment});
  }

  public delete(postId: string, commentId: string){
    return this.httpClient.delete(this.pathService +'/posts/'+postId+'/comments/'+commentId);
  }

  public update(postId: string, commentId: string, comment: string){
    return this.httpClient.put(this.pathService +'/posts/'+postId+'/comments/'+commentId, {content: comment});
  }

  public like(postId: string, commentId: string){
    return this.httpClient.post(this.pathService +'/posts/'+postId+'/comments/'+commentId+'/like', {});
  }
}
