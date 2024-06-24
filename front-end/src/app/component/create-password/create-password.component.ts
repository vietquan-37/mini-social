import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-create-password',
  templateUrl: './create-password.component.html',
  styleUrls: ['./create-password.component.css']
})
export class CreatePasswordComponent implements OnInit {
  @Input() password!:string|null;
  hidePassword = true;
  passwordForm!: FormGroup;
  user!: User;

  constructor(
    private fb: FormBuilder,
    private service: UserService,
    private router: Router,
    private snackBar: MatSnackBar 
  ) {}

  ngOnInit(): void {

    this.passwordForm = this.fb.group({
      password: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(16)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(16)]],
    });
  
    if (this.password !== null) {
    this.router.navigate(['']);
  }
  }

  togglePassword() {
    this.hidePassword = !this.hidePassword;
  }

  onSubmit() {
    if (this.passwordForm.valid) {
      if (this.passwordForm.value.password !== this.passwordForm.value.confirmPassword) {
        this.snackBar.open('Password not match', 'Close', { duration: 5000 });
      } else {
        this.service.createPassword(this.passwordForm.value).subscribe({
          next: (response: any) => {
            if (response.data != null) {
              this.passwordForm.reset();
              this.snackBar.open('Create password successfully', 'Close', { duration: 3000 });
              setTimeout(() => {
                this.router.navigate(['']);
              }, 2000); 
            }
          },
          error: (error: any) => {
            console.error('Error creating password:', error);
      
            this.snackBar.open(error.error, 'Close', { duration: 5000 });
          }
        });
      }
    }
  }
}
