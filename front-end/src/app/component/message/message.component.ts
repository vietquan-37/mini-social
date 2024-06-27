import { Component, Input  } from '@angular/core';
import { Conversation } from 'src/app/models/conversation';
import { Message } from 'src/app/models/message';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent {
  @Input() own!: boolean ;
  @Input() message!:Message |null
  @Input() conversation!:Conversation|null;
}
