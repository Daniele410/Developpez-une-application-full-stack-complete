import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import { AuthSuccess } from '../../interfaces/authSuccess.interface';
import { User } from 'src/app/interfaces/user.interface';
import { Component } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  public onError = false;

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    name: ['', [Validators.required, Validators.min(3)]],
    password: ['', [Validators.required, Validators.min(3)]]
  });

  constructor(private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService) { }

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe({
      next: (value: AuthSuccess) => this.router.navigate(['/login']),
      error: _ => this.onError = true,
    });
  }
}
