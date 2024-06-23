// user.service.ts
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api/user';

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
        }))
      );
  }

  private createHeader(): HttpHeaders {
    return new HttpHeaders().set(
      'Authorization', 'Bearer ' + localStorage.getItem('accessToken')
    );
  }
}
