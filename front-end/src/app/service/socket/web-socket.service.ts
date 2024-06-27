// src/app/services/websocket.service.ts
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Client, IMessage } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { Message } from 'src/app/models/message';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private client!: Client;
  private messageSubjects: { [chatId: number]: Subject<Message> } = {};

  constructor() {
    this.initializeWebSocketConnection();
  }

  private initializeWebSocketConnection(): void {
    this.client = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      debug: (str) => {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000
    });

    this.client.onConnect = (frame) => {
      console.log('Connected: ' + frame);
    };

    this.client.activate();
  }

  public sendMessage(chatId: string, message: any): void {
    this.client.publish({
      destination: `/app/chat/${chatId}`,
      body: JSON.stringify(message)
    });
  }

  public subscribeToChat(chatId: number): Observable<Message> {
    if (!this.messageSubjects[chatId]) {
      this.messageSubjects[chatId] = new Subject<Message>();
      this.client.subscribe(`/user/${chatId}/private`, (message: IMessage) => {
        this.messageSubjects[chatId].next(JSON.parse(message.body));
      });
    }
    return this.messageSubjects[chatId].asObservable();
  }
}
