import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, shareReplay } from 'rxjs';
import { Topics } from '../interfaces/topics.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicsService {

  private pathService = 'http://localhost:8080/api'; 

  constructor(private http: HttpClient) {}
  private cachedTopics$?: Observable<Topics[]>;

  public getTopics(): Observable<Topics[]> {
    return this.http.get<Topics[]>(this.pathService + '/topics');
    
  }

  public findOne(id: string): Observable<Topics> {
    return this.http.get<Topics>(this.pathService + `/topics/${id}`);
  }

  public getUserSubscribedTopics(): Observable<Topics[]> {
    return this.http.get<Topics[]>(this.pathService + '/topics/subscribe');
  }

  public isUserSubscribedToTopic(topicsId: number): Observable<boolean> {
    return this.http.get<boolean>(this.pathService + `/topics/${topicsId}/is-subscribed`);
  }
}
