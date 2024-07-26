// user.service.ts
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api/user';
  private userSubject: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null);

  constructor(private http: HttpClient) {}

  getUserInfo(): Observable<User> {
    return this.http.get<any>(`${this.baseUrl}/info`, { headers: this.createHeader() })
      .pipe(
        map(response => ({
          id: response.data.id,
          name: response.data.name,
          avatarUrl: response.data.avatarUrl,
          email: response.data.email,
          password: response.data.password,
          phone: response.data.phone
        })),
        tap(user => this.userSubject.next(user))
      );
  }

  getUser(): Observable<User | null> {
    return this.userSubject.asObservable();
  }

  createPassword(dto: { password: string, confirmPassword: string }): Observable<any> {
    return this.http.patch<any>(`${this.baseUrl}/create-password`, dto, { headers: this.createHeader() });
  }

  private createHeader(): HttpHeaders {
    return new HttpHeaders().set(
      'Authorization', 'Bearer ' + localStorage.getItem('accessToken')
    );
  }
}
