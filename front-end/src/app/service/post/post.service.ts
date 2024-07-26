import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from 'src/app/models/post';
import { map } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class PostService {
  private baseUrl = 'http://localhost:8080/api/post';
  constructor(private http: HttpClient) { }
  getAllPost(): Observable<Post[]> {
    return this.http.get<any>(`${this.baseUrl}`, { headers: this.createHeader() })
    .pipe(
      map(response => response.data.map((data: any) => ({
          userId: data.userId,
          userImage:data.userImage,
          postId: data.postId,
          content: data.content,
          imageUrl: data.imageUrl,
          username: data.username,
          publishDate: data.publishDate

        })))
      );
  }
  createPost(dto: { content:string}): Observable<number> {
    return this.http.post<number>(`${this.baseUrl}`, dto, { headers: this.createHeader() });
  }


  private createHeader(): HttpHeaders {
    return new HttpHeaders().set(
      'Authorization', 'Bearer ' + localStorage.getItem('accessToken')
    );
  }
}
