import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Router } from '@angular/router';

import { AuthenticateService } from '../../service/auth/authenticate.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  formData!: FormGroup;
  errorMessage!: string;
  @ViewChild('confirmationDialog', { static: true }) confirmationDialog!: TemplateRef<any>;

  constructor(
    private fb: FormBuilder,
    private service: AuthenticateService,
    private router: Router,
  
  ) { }

  ngOnInit(): void {
    this.formData = this.fb.group({
      name: ['', Validators.required],
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(16)]],
      phone: ['', [Validators.required, Validators.pattern('\\d{10,11}')]]
    });
  }

  onSubmit(): void {
    if (this.formData.valid) {
      this.service.register(this.formData.value).subscribe(
        (response: any) => {
          this.formData.reset();
          this.router.navigate(['login']);
        },
        (error) => {
          this.errorMessage = error.error?.error;
        }
      );
    }
  }

 
}
