import { NgIf } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from './service/auth-service';
import { HttpStatusCode } from '@angular/common/http';
import { Router, RouterOutlet } from '@angular/router';
import { Toast } from './shared/toast/toast';
import { ConfirmDialog } from './shared/confirm-dialog/confirm-dialog';
import { ToastService } from './service/toast.service';

@Component({
  selector: 'app-root',
  imports: [NgIf, ReactiveFormsModule, FormsModule, RouterOutlet, Toast, ConfirmDialog],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  showRegister: boolean = false;
  loginForm: FormGroup;
  registerForm: FormGroup;
  private toast = inject(ToastService);

  constructor(private auth: AuthService, private form: FormBuilder, private router: Router) {
    this.loginForm = form.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.registerForm = this.form.group({
      userName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  toggleForm() { this.showRegister = !this.showRegister; }

  onLogin() {
    this.auth.login(this.loginForm.value).subscribe({
      next: res => {
        if (res.status == HttpStatusCode.Ok) {
          const body = res.body as any;
          this.toast.success('Welcome back! You are now logged in.', 'Login Successful');
          this.loginForm.reset();
          this.auth.setLoginStatus(true);
          this.auth.setJwtToken(body.token);
          this.auth.setRefereshToken(body.refreshToken);
          console.log(body.token);
          this.router.navigate(['/dashboard']);
        }
      },
      error: err => {
        if (err.status === 400) {
          this.toast.error(err.error?.errors?.email || 'Invalid email address.', 'Validation Error');
        } else if (err.status === 404) {
          this.toast.error(err.error?.message || 'User not found.', 'Login Failed');
        } else {
          this.toast.error('Unable to reach server. Please try again.', 'Server Down');
        }
        this.loginForm.reset();
        this.auth.setLoginStatus(false);
      }
    });
  }

  onRegister() {
    if (this.registerForm.value.confirmPassword !== this.registerForm.value.password) {
      this.toast.warning('Passwords do not match. Please try again.', 'Password Mismatch');
      return;
    }
    const payload = {
      userName: this.registerForm.get('userName')?.value,
      email: this.registerForm.get('email')?.value,
      password: this.registerForm.get('password')?.value
    };
    this.auth.register(payload).subscribe({
      next: response => {
        if (response.status == HttpStatusCode.Created) {
          this.toast.success('Account created successfully! Please sign in.', 'Registered');
          this.registerForm.reset();
          this.showRegister = false;
        }
      },
      error: err => {
        if (err.error?.status === 404) {
          this.toast.error(err.error?.message, 'Registration Failed');
        } else {
          this.toast.error('Unable to reach server. Please try again.', 'Server Down');
        }
      }
    });
  }

  getLoginStatus() { return this.auth.getLoginStatus(); }
}
