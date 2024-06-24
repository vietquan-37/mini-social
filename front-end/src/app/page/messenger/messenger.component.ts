import { Component, OnInit } from '@angular/core';
import { Conversation } from 'src/app/models/conversation';
import { ChatService } from 'src/app/service/chat/chat.service';

@Component({
  selector: 'app-messenger',
  templateUrl: './messenger.component.html',
  styleUrls: ['./messenger.component.css']
})
export class MessengerComponent implements OnInit {
  conversations: Conversation[] = [];

  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    this.getConversations();
  }

  getConversations(): void {
    this.chatService.getUserChat().subscribe(
      (conversations: Conversation[]) => {
        this.conversations = conversations;
        console.log(conversations)
      },
      error => {
        console.error('Error fetching conversations', error);
     
      }
    );
  }
}
