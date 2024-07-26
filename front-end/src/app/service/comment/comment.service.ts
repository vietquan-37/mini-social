import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Comment } from 'src/app/models/comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private baseUrl = 'http://localhost:8080/api/comment';
  constructor(private http: HttpClient) { }
  getAllComment(postId:number): Observable<Comment[]> {
    return this.http.get<any>(`${this.baseUrl}/${postId}`, { headers: this.createHeader() })
    .pipe(
      map(response => response.data.map((data: any) => ({
         id:data.id,
         userId:data.userId,
          username: data.username,
          userImage: data.userImage,
          content: data.content,
          createTime: data.createTime

        })))
      );
  }
  createComment(content:string,postId:number): Observable<Comment> {
    const dto = { content };
    return this.http.post<any>(`${this.baseUrl}/${postId}`, dto, { headers: this.createHeader() }).pipe(
      map(response => ({
        id: response.data.id,
         userId: response.data.userId,
          username:  response.data.username,
          userImage:  response.data.userImage,
          content:  response.data.content,
          createTime:  response.data.createTime
  
      }))
    );
  }
  private createHeader(): HttpHeaders {
    return new HttpHeaders().set(
      'Authorization', 'Bearer ' + localStorage.getItem('accessToken')
    );
  }
}
