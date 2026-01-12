import { Component } from '@angular/core';
import { AuthService } from '../../../service/auth/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { RequestLogin } from '../../../model/request-login';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private authService: AuthService, private router: Router) {
  }

  signInData = {
    email: '',
    password: ''
  };
  emailError = '';
  passwordError = '';
  errorMessageAr = '';
  errorMessageEn = '';
  errorBackend = false;

  resetErrorFields(): void {
    this.emailError = '';
    this.passwordError = '';
  }

  onSubmit(): void {
    if (!this.validation(this.signInData.email, this.signInData.password)) {
      return;
    }
    const requestLoginModel = new RequestLogin(
      this.signInData.email, this.signInData.password
    );
    this.authService.login(requestLoginModel).subscribe({
      next: response => {
        this.errorBackend = false;
        this.resetErrorFields();
        sessionStorage.setItem('account_id', response.data.body.id);
        sessionStorage.setItem('token', response.data.body.token);
        sessionStorage.setItem('userName', response.data.body.username);
        this.router.navigateByUrl('mainpage');
      }, error: errors => {
        this.errorBackend = true;
        this.resetErrorFields();
        this.errorMessageAr = errors.error.bundleMessage.message_ar;
        this.errorMessageEn = errors.error.bundleMessage.message_en;
      }
    });
  }

  validation(email: string, password: string): boolean {
    if (email === '') {
      this.errorBackend = false;
      this.emailError = 'Email is required';
      return false;
    }
    if (password === '') {
      this.errorBackend = false;
      this.resetErrorFields();
      this.passwordError = 'Password is required';
      return false;
    }
    return true;
  }

  clear(): void {
    this.emailError = '';
    this.passwordError = '';
    this.errorMessageAr = '';
    this.errorMessageEn = '';
    this.errorBackend = false;
  }
}
