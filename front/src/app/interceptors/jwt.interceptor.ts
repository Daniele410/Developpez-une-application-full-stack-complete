import { HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SessionService } from '../services/session.service';
import { catchError, throwError } from "rxjs";
import { Router } from "@angular/router";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
  constructor(private sessionService: SessionService, private router:Router) {}

  public intercept(request: HttpRequest<any>, next: HttpHandler) {
    if (this.sessionService.isLogged()) {
      request = request.clone({
        setHeaders: { 
          "content-type": "application/json",
          Authorization: `Bearer ${sessionStorage.getItem('token')}`,
        },
      });
    }
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) { // 401 is the status code for "Unauthorized"
          this.router.navigate(['/login']); // Redirect to login URL
        }
        return throwError(error);
      })
    );
  }
}
