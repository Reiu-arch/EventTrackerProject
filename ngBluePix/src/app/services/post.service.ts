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
          () => new Error('todoService.index(): error showing todos: ' + err)
        );
      })
    );
  }





}
