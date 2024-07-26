import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Comment } from 'src/app/models/comment';
import { User } from 'src/app/models/user';
import { CommentService } from 'src/app/service/comment/comment.service';
import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit, OnChanges {

  @Input() postId!: number;
  user: User | null = null;
  content: string = '';
  comments: Comment[] = [];

  constructor(private service: CommentService, private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getUser().subscribe(
      (data: User | null) => {
        this.user = data;
      }
    );
    this.getAllComment();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['postId'] && !changes['postId'].isFirstChange()) {
      this.getAllComment();
    }
  }

  getAllComment() {
    this.service.getAllComment(this.postId).subscribe(
      (comments: Comment[]) => {
        this.comments = comments;
      },
      error => {
        console.error('Error fetching comments', error);
      }
    );
  }

  addComment() {
    if (!this.content.trim()) {
      return;
    }
    this.service.createComment(this.content, this.postId).subscribe(
      (comment: Comment) => {
        this.comments.push(comment);
        this.content = '';
      },
      error => {
        console.error('Error adding comment', error);
      }
    );
  }
}
