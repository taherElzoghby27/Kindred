
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../service/auth/auth.service';
import { Router, RouterModule } from '@angular/router';
import { RequestSignUp } from '../../../model/request-sign-up';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {

  constructor(private authService: AuthService, private router: Router) {
  }

  signupData = {
    username: '',
    email: '',
    password: '',
    confirmPassword: ''
  };
  usernameError = '';
  emailError = '';
  passwordError = '';
  confirmPasswordError = '';
  errorMessageAr = '';
  errorMessageEn = '';
  errorBackend = false;

  resetErrorFields(): void {
    this.usernameError = '';
    this.passwordError = '';
    this.emailError = '';
    this.confirmPasswordError = '';
  }

  onSubmit(): void {
    if (!this.validation(this.signupData.username, this.signupData.email, this.signupData.password, this.signupData.confirmPassword)) {
      return;
    }
    const requestSignupModel = new RequestSignUp(
      this.signupData.username, this.signupData.email, this.signupData.password
    );
    this.authService.signUp(requestSignupModel).subscribe(
      response => {
        this.errorBackend = false;
        this.resetErrorFields();
        sessionStorage.setItem('account_id', response.data.body.id);
        sessionStorage.setItem('token', response.data.body.token);
        sessionStorage.setItem('userName', response.data.body.username);
        this.router.navigateByUrl('mainpage');
      }, errors => {
        this.errorBackend = true;
        this.resetErrorFields();
        this.errorMessageAr = errors.error.bundleMessage.message_ar;
        this.errorMessageEn = errors.error.bundleMessage.message_en;
      });
  }

  validation(username: string, email: string, password: string, confirmPassword: string): boolean {
    if (username === '') {
      this.errorBackend = false;
      this.usernameError = 'Username is required';
      return false;
    }
    if (email === '') {
      this.errorBackend = false;
      this.resetErrorFields();
      this.emailError = 'Email is required';
      return false;
    }
    if (password === '') {
      this.errorBackend = false;
      this.resetErrorFields();
      this.passwordError = 'Password is required';
      return false;
    }
    if (confirmPassword === '') {
      this.errorBackend = false;
      this.resetErrorFields();
      this.confirmPasswordError = 'Confirm Password is required';
      return false;
    }
    if (confirmPassword !== password) {
      this.errorBackend = false;
      this.resetErrorFields();
      this.confirmPasswordError = 'Confirm Password not match password';
      return false;
    }
    return true;
  }

  clear(): void {
    this.usernameError = '';
    this.emailError = '';
    this.passwordError = '';
    this.confirmPasswordError = '';
    this.errorMessageAr = '';
    this.errorMessageEn = '';
    this.errorBackend = false;
  }

}
