import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {PostRequest} from '../../model/post-request';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  baseUrl = 'http://localhost:7070/posts/';
  baseUrlMedia = 'http://localhost:7070/uploads/';

  constructor(private http: HttpClient) {
  }

  createPost(postRequest: PostRequest): Observable<any> {
    const formData = new FormData();
    formData.append('content', postRequest.content);
    formData.append('media', postRequest.media);
    return this.http.post<any>(`${this.baseUrl}create-post`, formData).pipe(
      map(response => response)
    );
  }
}
