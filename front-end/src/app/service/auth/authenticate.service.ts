import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {
  private baseUrl = 'http://localhost:8080/api/auth';
  private loggedInSubject = new BehaviorSubject<boolean>(this.isLoggedIn());

  loggedIn$ = this.loggedInSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  isLoggedIn() {
    return !!this.getAccessToken() && !this.isTokenExpired();
  }

  addAccessToken(accessToken: string) {
    localStorage.setItem('accessToken', accessToken);
  }

  getAccessToken() {
    return localStorage.getItem('accessToken');
  }

  getRefreshToken() {
    return localStorage.getItem('refreshToken');
  }

  logout() {
    localStorage.clear();
    this.loggedInSubject.next(false);
    this.router.navigate(['/login']);
  }

  isTokenExpired() {
    const token: string = this.getAccessToken() ?? '';
    if (!token) return false;
    const tokenSplit: string = token.split('.')[1];
    const decodedString: string = atob(tokenSplit);
    const jsonString = JSON.parse(decodedString);
    const expiry = jsonString.exp;
    return (Math.floor((new Date).getTime() / 1000)) >= expiry;
  }

  authenticateGoogle(code: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/outbound/google/authentication?code=${code}`, code)
      .pipe(
        tap((response: any) => {
          localStorage.setItem('userId', response.data.userId?.toString() || '');
          localStorage.setItem('accessToken', response.data.accessToken as string);
          localStorage.setItem('refreshToken', response.data.refreshToken as string);
          localStorage.setItem('role', response.data.role as string);
          this.loggedInSubject.next(true);
        }),
        catchError(error => {
          console.error('An error occurred:', error);
          return throwError(error);
        })
      );
  }

  authenticateGitHub(code: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/outbound/github/authentication?code=${code}`, code)
      .pipe(
        tap((response: any) => {
          localStorage.setItem('userId', response.data.userId?.toString() || '');
          localStorage.setItem('accessToken', response.data.accessToken as string);
          localStorage.setItem('refreshToken', response.data.refreshToken as string);
          localStorage.setItem('role', response.data.role as string);
          this.loggedInSubject.next(true);
        }),
        catchError(error => {
          console.error('An error occurred:', error);
          return throwError(error);
        })
      );
  }

  register(inputData: any) {
    return this.http.post<any>(`${this.baseUrl}/register`, inputData);
  }

  authenticate(inputData: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/authenticate`, inputData)
      .pipe(
        tap((response: any) => {
          localStorage.setItem('userId', response.data.userId?.toString() || '');
          localStorage.setItem('accessToken', response.data.accessToken as string);
          localStorage.setItem('refreshToken', response.data.refreshToken as string);
          localStorage.setItem('role', response.data.role as string);
          this.loggedInSubject.next(true);
        })
      );
  }
}
