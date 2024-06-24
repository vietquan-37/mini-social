import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticateService } from '../../service/auth/authenticate.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hidePassword = true;

  loginForm!: FormGroup;

  constructor(private fb: FormBuilder,
    private service: AuthenticateService,
    private router: Router,
    private snackBar: MatSnackBar

  ) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(16)]],
    })

  }
  handleGoogle(): void {
    const callbackUrl = "http://localhost:4200/authenticate";
    const authUrl = "https://accounts.google.com/o/oauth2/auth";
    const googleClientId = "304094440461-shiv5qppkpk2d72pk0bf3ff918tt5qil.apps.googleusercontent.com";

    const targetUrl = `${authUrl}?redirect_uri=${encodeURIComponent(
      callbackUrl
    )}&response_type=code&client_id=${googleClientId}&scope=openid%20email%20profile`;

    console.log(targetUrl);

    window.location.href = targetUrl;
  }

  handleGitHub(): void {
    const callbackUrl = "http://localhost:4200/authenticate";
    const authUrl = "https://github.com/login/oauth/authorize";
    const githubClientId = "Ov23ctLi3AiglvaVhxZL";
    const scopes = "user";

    const targetUrl = `${authUrl}?client_id=${githubClientId}&redirect_uri=${encodeURIComponent(callbackUrl)}&scope=${encodeURIComponent(scopes)}`;

    window.location.href = targetUrl;
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.service.authenticate(this.loginForm.value).subscribe((response: any) => {
        this.router.navigate(['']);
      }
        ,
        (error) => {
          this.snackBar.open(error?.error.error, 'Close', { duration: 3000 })
        }

      )
    }
  }
  togglePassword() {
    this.hidePassword = !this.hidePassword;
    const inputElement = document.querySelector('input[formControlName="password"]') as HTMLInputElement; // Cast the element to HTMLInputElement to avoid the 'possibly 'null'' error
    inputElement.type = this.hidePassword ? 'password' : 'text';
  }

}