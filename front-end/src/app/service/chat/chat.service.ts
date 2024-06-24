import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Conversation } from 'src/app/models/conversation';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private baseUrl = 'http://localhost:8080/api/chats';
  constructor(private http:HttpClient) { }
  private createHeader(): HttpHeaders {
    return new HttpHeaders().set(
      'Authorization', 'Bearer ' + localStorage.getItem('accessToken')
    );
  }
  getUserChat(): Observable<Conversation[]> {
    return this.http.get<any>(`${this.baseUrl}/user`, { headers: this.createHeader() })
      .pipe(
        map(response => response.data.map((data: any) => ({
          chatId: data.chatId,
          userId: data.userId,
          userImage: data.userImage,
          name: data.name,
         
        })))
      );
  }
}
