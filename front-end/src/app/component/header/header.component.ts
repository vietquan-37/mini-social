import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { AuthenticateService } from 'src/app/service/auth/authenticate.service';
import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  user: User | null = null;

  constructor(private userService: UserService,
    private authService: AuthenticateService
  ) { }

  ngOnInit(): void {
    this.userService.getUser().subscribe(
      (data: User | null) => {
        this.user = data;
    
      }
    );
  }
  logout() {
    this.authService.logout();
  }

}