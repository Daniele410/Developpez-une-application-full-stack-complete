import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../interfaces/post';
import { PostRequest } from '../interfaces/post-request';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private pathService = "http://localhost:8080/api";

  constructor(private httpClient: HttpClient) { }

  public getPosts(): Observable<Post[]>{
    return this.httpClient.get<Post[]>(this.pathService +'/posts');
  }

  public detail(id:string): Observable<Post>{
    return this.httpClient.get<Post>(`${this.pathService +'/posts' }/${id}`);
  }

  public create(post: PostRequest): Observable<Post>{
    return this.httpClient.post<Post>(this.pathService +'/posts', post)
  }
}
