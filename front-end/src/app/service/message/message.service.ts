import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Message } from 'src/app/models/message';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private baseUrl = 'http://localhost:8080/api/message';

  constructor(private http: HttpClient) {}
  private createHeader(): HttpHeaders {
    return new HttpHeaders().set(
      'Authorization', 'Bearer ' + localStorage.getItem('accessToken')
    );
  }
  getAllMessage(chatId:number): Observable<Message[]> {
    return this.http.get<any>(`${this.baseUrl}/${chatId}`, { headers: this.createHeader() })
      .pipe(
        map(response => response.data.map((data: any) => ({
          senderId: data.senderId,
          content: data.content,
          timestamp: data.timestamp,
       
         
        })))
      );
  }
  sendMessage(content: string, chatId: number): Observable<Message> {
    const dto = { content };
    return this.http.post<any>(`${this.baseUrl}/create/${chatId}`, dto, { headers: this.createHeader() })
      .pipe(
        map(response => ({
          senderId: response.data.senderId,
          content: response.data.content,
          timestamp: response.data.timestamp,
    
        }))
      );
  }

}
