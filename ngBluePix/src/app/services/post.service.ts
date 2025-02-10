import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Post } from '../models/posts';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private url = environment.baseUrl + 'api/posts';

  constructor(
    private http: HttpClient
  ) { }


  index(): Observable<Post[]> {
    return this.http.get<Post[]>(this.url + '?sorted=true').pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error('postService.index(): error showing posts: ' + err)
        );
      })
    );
  }

  create(post: Post) : Observable<Post> {

    return this.http.post<Post>(this.url, post).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error('postService.create(): error creating post: ' + err)
        );
      })
    );
  }

  update(post: Post) : Observable<Post> {

  return this.http.put<Post>(`${this.url}/${post.id}`, post).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError(
        () => new Error('postService.update(): error updating post: ' + err)
      );
    })
  );
}



}
