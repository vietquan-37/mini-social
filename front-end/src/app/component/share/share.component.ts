import { Component ,Input, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from 'src/app/models/user';
import { PostService } from 'src/app/service/post/post.service';
import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-share',
  templateUrl: './share.component.html',
  styleUrls: ['./share.component.css']
})
export class ShareComponent  implements OnInit{
  user: User | null = null;
  shareForm!:FormGroup
  constructor(private builder:FormBuilder,
    private service:PostService,
    private userService:UserService,
    private snackBar:MatSnackBar
  ){

  }

  
  ngOnInit(): void {
    this.userService.getUser().subscribe(
      (data: User | null) => {
        this.user = data;
     
      }
    );
    this.shareForm=this.builder.group({
      content:['',Validators.required]
    })
  }
  addPost() {
    this.service.createPost(this.shareForm.value).subscribe((res: number) => {
      this.shareForm.reset();
      const snackBarRef = this.snackBar.open('Post successfully added', 'Close', {
        duration: 2000,
        panelClass: ['no-pointer']  
      });
      snackBarRef.afterDismissed().subscribe(() => {
        window.location.reload();  
      });
    });
  }
  
  
}
