import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RequestSignUp} from '../../model/request-sign-up';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {RequestLogin} from '../../model/request-login';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  baseUrl = 'http://localhost:7070/auth/';

  constructor(private http: HttpClient) {
  }

  isLogin(): boolean {
    return sessionStorage.getItem('token') !== null && sessionStorage.getItem('token') !== undefined;
  }

  getCurrentUser(): string {
    return sessionStorage.getItem('userName');
  }

  logout(): void {
    sessionStorage.removeItem('token');
  }

  signUp(requestSignUpModel: RequestSignUp): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}sign-up`, requestSignUpModel).pipe(
      map(response =>
        response
      )
    );
  }

  login(requestLoginModel: RequestLogin): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}login`, requestLoginModel).pipe(
      map(response => response)
    );
  }

  // getProfile(): Observable<Profile> {
  //   return this.http.get<Profile>(`${this.baseUrl}profile`).pipe(
  //     map(response => response)
  //   );
  // }

  // updateProfile(profile: Profile): Observable<any> {
  //   return this.http.put<any>(`${this.baseUrl}update-profile`, profile).pipe(
  //     map(response => response)
  //   );
  // }
}
