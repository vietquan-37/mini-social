import { Component,Input, OnInit } from '@angular/core';
import { Conversation } from 'src/app/models/conversation';

@Component({
  selector: 'app-conversation',
  templateUrl: './conversation.component.html',
  styleUrls: ['./conversation.component.css']
})
export class ConversationComponent implements OnInit{
  @Input() conversation!: Conversation;
  ngOnInit(): void {
    console.log(this.conversation)
  }
}
