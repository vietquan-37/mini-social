import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable, tap, throwError} from "rxjs";
import {catchError} from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {
  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(
    private http: HttpClient,

  ) {}

  authenticateGoogle(code: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/outbound/google/authentication?code=${code}`,code)
      .pipe(
        tap((response: any) => {
          localStorage.setItem('userId', response.data.userId?.toString() || '');
          localStorage.setItem('accessToken', response.data.accessToken as string);
          localStorage.setItem('refreshToken', response.data.refreshToken as string);
          localStorage.setItem('role', response.data.role as string);
        }),
        catchError(error => {
          // Handle error appropriately, e.g., log it or show user-friendly message
          console.error('An error occurred:', error);
          // Rethrow it to keep observable chain alive
          return throwError(error);
        })
      );
  }
  authenticateFacebook(code: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/outbound/github/authentication?code=${code}`,code)
      .pipe(
        tap((response: any) => {
          localStorage.setItem('userId', response.data.userId?.toString() || '');
          localStorage.setItem('accessToken', response.data.accessToken as string);
          localStorage.setItem('refreshToken', response.data.refreshToken as string);
          localStorage.setItem('role', response.data.role as string);
        }),
        catchError(error => {
      
          console.error('An error occurred:', error);
        
          return throwError(error);
        })
      );
  }
  register(inputData:any){
    return this.http.post<any>(`${this.baseUrl}/register`,inputData);
  }
  authenticate(inputData: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/authenticate`, inputData)
      .pipe(
        tap((response: any) => {
          localStorage.setItem('userId', response.data.userId?.toString() || '');
          localStorage.setItem('accessToken', response.data.accessToken as string);
          localStorage.setItem('refreshToken', response.data.refreshToken as string);
          localStorage.setItem('role', response.data.role as string);
       


        })
      );
  }
}