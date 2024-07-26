import { Component, Input, OnInit } from '@angular/core';
import { Post } from 'src/app/models/post';
import { User } from 'src/app/models/user';
import { PostService } from 'src/app/service/post/post.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
  post: Post[] = [];
  @Input() users: User | undefined;

  constructor(private service: PostService) {}

  ngOnInit(): void {
    this.getAllPost();
  }

  getAllPost() {
    this.service.getAllPost().subscribe(
      (post: Post[]) => {
        this.post = post;
        console.log(post);
      },
      error => {
        console.error('Error fetching posts', error);
      }
    );
  }
}
