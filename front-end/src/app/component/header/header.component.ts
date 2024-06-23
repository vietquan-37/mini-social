import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent  {
  @Input() users: User | undefined;
  defaultAvatarUrl: string = './assets/image/default-avatar.jpg';

  constructor() {}


}