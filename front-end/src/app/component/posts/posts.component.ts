import { Component, Input, OnInit } from '@angular/core';
import { Post } from 'src/app/models/post';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  @Input() posts: Post[] = [];
  @Input() user: User | undefined;
  
 
  commentOpenStates: { [postId: number]: boolean } = {};

  ngOnInit(): void {}

  toggleComments(postId: number): void {
    this.commentOpenStates[postId] = !this.commentOpenStates[postId];
  }

  isCommentOpen(postId: number): boolean {
    return this.commentOpenStates[postId];
  }
}
