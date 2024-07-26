import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user: User | null = null;

  constructor(private userService: UserService) { }
ngOnInit(): void {
  this.getUserInfo()
}
  getUserInfo() {
    this.userService.getUserInfo().subscribe(
      (data: User) => {
        this.user = data;
     
      },
      (error) => {
        console.error('Error fetching user info:', error);
      }
    );
  }
}
