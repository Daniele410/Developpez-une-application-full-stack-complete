import { HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SessionService } from '../services/session.service';

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
  constructor(private sessionService: SessionService) {}

  public intercept(request: HttpRequest<any>, next: HttpHandler) {
    if (this.sessionService.isLogged()) {
      request = request.clone({
        setHeaders: { 
          "content-type": "application/json",
          Authorization: `Bearer ${sessionStorage.getItem('token')}`,
        },
      });
    }
    return next.handle(request);
  }
}