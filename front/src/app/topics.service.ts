import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, shareReplay } from 'rxjs';
import { Topics } from './interfaces/topics.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicsService {

  constructor(private http: HttpClient) {}
  private cachedTopics$?: Observable<Topics[]>;

  public getTopics(): Observable<Topics[]> {
    if (!this.cachedTopics$) {
      this.cachedTopics$ = this.http.get<Topics[]>('/api/topics').pipe(shareReplay({ refCount: true, bufferSize: 1 }));
    }

    return this.cachedTopics$;
  }

  public findOne(id: string): Observable<Topics> {
    return this.http.get<Topics>(`/api/topics/${id}`);
  }

  public getUserSubscribedTopics(): Observable<Topics[]> {
    return this.http.get<Topics[]>('/api/topics/mySubscriptions');
  }

  public subscribeToTopic(topicsId: number): Observable<void> {
    return this.http.post<void>(`/api/topics/${topicsId}/subscribe`, {});
  }

  public unsubscribeFromTopics(topicsId: number): Observable<void> {
    return this.http.delete<void>(`/api/topics/${topicsId}/unsubscribe`);
  }
}
