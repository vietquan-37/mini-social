import { Component,Input } from '@angular/core';
import { Conversation } from 'src/app/models/conversation';

@Component({
  selector: 'app-conversation',
  templateUrl: './conversation.component.html',
  styleUrls: ['./conversation.component.css']
})
export class ConversationComponent {
  @Input() conversation!: Conversation;
}
