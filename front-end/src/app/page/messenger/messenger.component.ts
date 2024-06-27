import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Conversation } from 'src/app/models/conversation';
import { Message } from 'src/app/models/message';
import { ChatService } from 'src/app/service/chat/chat.service';
import { MessageService } from 'src/app/service/message/message.service';
import { WebSocketService } from 'src/app/service/socket/web-socket.service';


@Component({
  selector: 'app-messenger',
  templateUrl: './messenger.component.html',
  styleUrls: ['./messenger.component.css']
})
export class MessengerComponent implements OnInit {
  conversations: Conversation[] = [];
  currentConversation: Conversation | null = null;
  currentConversationMessages: Message[] = [];
  userId!: number;
  messageContent: string = '';

  @ViewChild('chatContainer') private chatContainerRef!: ElementRef;

  constructor(
    private chatService: ChatService,
    private messageService: MessageService,
    private webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {
    const userIdStr = localStorage.getItem('userId');
    this.userId = Number(userIdStr);
    this.getConversations();
  }

  getConversations(): void {
    this.chatService.getUserChat().subscribe(
      (conversations: Conversation[]) => {
        this.conversations = conversations;
      },
      error => {
        console.error('Error fetching conversations', error);
      }
    );
  }

  selectConversation(conversation: Conversation): void {
    this.currentConversation = conversation;
    this.messageService.getAllMessage(conversation.chatId).subscribe(
      (messages: Message[]) => {
        this.currentConversationMessages = messages;
        this.scrollToEnd();
      },
      error => {
        console.error('Error fetching messages', error);
      }
    );

    // Subscribe to WebSocket messages for this conversation
    this.webSocketService.subscribeToChat(conversation.chatId).subscribe(
      (message: Message) => {
        this.currentConversationMessages.push(message);
        this.scrollToEnd();
      },
      error => {
        console.error('Error receiving real-time messages', error);
      }
    );
  }

  scrollToEnd():void{
    setTimeout(() => {
      const chatBoxTop = document.querySelector('.chatBoxTop');
      if (chatBoxTop) {
        chatBoxTop.scrollTop = chatBoxTop.scrollHeight;
      }
    }, 0);
  }

  sendMessage(): void {
    if (!this.messageContent.trim() || !this.currentConversation) {
      return;
    }

    const chatId = this.currentConversation.chatId;

    this.messageService.sendMessage(this.messageContent, chatId).subscribe(
      (newMessage: Message) => {
        console.log('New message:', newMessage);
        this.messageContent = '';
        this.currentConversationMessages.push(newMessage);
        this.webSocketService.sendMessage(chatId.toString(), newMessage);
        this.scrollToEnd();
      },
      error => {
        console.error('Error sending message', error);
      }
    );
  }
}
