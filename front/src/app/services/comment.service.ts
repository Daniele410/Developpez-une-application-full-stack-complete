import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../interfaces/comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private pathService = "http://localhost:8080/api";

  constructor(private httpClient: HttpClient) { }

  public getComments(postId: string): Observable<Comment[]> { 
    return this.httpClient.get<Comment[]>(this.pathService +'/comments/'+postId);
  }

  public create(postId: string ,comment: Comment): Observable<Comment>{
    return this.httpClient.post<Comment>(this.pathService +'/comment', comment)
  }


}
