import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Topics } from '../interfaces/topics.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private pathService = "http://localhost:8080/api/topics";

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<Topics[]>{
    return this.httpClient.get<Topics[]>(this.pathService);
  }

  public detail(id:string): Observable<Topics>{
    return this.httpClient.get<Topics>(`${this.pathService}/${id}`);
  }

  public create(topic: Topics): Observable<Topics>{
    return this.httpClient.post<Topics>(this.pathService, topic)
  }
}
