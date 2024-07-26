import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticateService } from '../../service/auth/authenticate.service';

@Component({
  selector: 'app-authenticate',
  templateUrl: './authenticate.component.html',
  styleUrls: ['./authenticate.component.css']
})
export class AuthenticateComponent implements OnInit {
  isLoading = true;
  hasError = false;
  errorMessage!:string

  constructor(
    private service: AuthenticateService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const authCodeRegex = /code=([^&]+)/;
    const isMatch = window.location.href.match(authCodeRegex);
    
    if (isMatch) {
      const code = isMatch[1];
      if (window.location.href.includes('google')) {
        this.authenticateWithGoogle(code);
      }  else {
       this.authenticateWithFacebook(code);
      }
    }
  }

  authenticateWithGoogle(code: string): void {
    this.service.authenticateGoogle(code).subscribe(
      () => {
        this.isLoading = false;
        this.router.navigate(['/']);
      },
      (error) => {
        this.errorMessage=error?.error.error
        this.handleError(error.message);
      }
    );
  }

  authenticateWithFacebook(code: string): void {
    this.service.authenticateGitHub(code).subscribe(
      () => {
        this.isLoading = false;
        this.router.navigate(['/']);
      },
      (error) => {
        this.errorMessage=error?.error.error
        this.handleError(error.message);
      }
    );
  }

  handleError(message: string): void {
    console.error(message);
    this.isLoading = false;
    this.hasError = true;
  }
}
