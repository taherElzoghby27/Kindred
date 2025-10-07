import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {PostRequest} from '../../model/post-request';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {PostResponse} from '../../model/post-response';
import {GeneralResponse} from '../../model/general-response';

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
    if (postRequest.content) {
      formData.append('content', postRequest.content);
    }
    if (postRequest.media) {
      formData.append('media', postRequest.media, postRequest.media.name);
    }
    return this.http.post<any>(`${this.baseUrl}create-post`, formData).pipe(
      map(response => response)
    );
  }

  getAllPosts(page: number, pageSize: number): Observable<GeneralResponse<PostResponse>> {
    return this.http.get<any>(`${this.baseUrl}get-all-posts`, {
      params: {
        page: page.toString(),
        pageSize: pageSize.toString(),
      }
    }).pipe(
      map(response => response.data.body)
    );
  }

  getMyPosts(page: number, pageSize: number): Observable<GeneralResponse<PostResponse>> {
    return this.http.get<any>(`${this.baseUrl}get-my-posts`, {
      params: {
        page: page.toString(),
        pageSize: pageSize.toString(),
      }
    }).pipe(
      map(response => response.data.body)
    );
  }

  deletePost(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}delete-post`, {
      params: {
        id: id.toString()
      }
    }).pipe(
      map(response => response)
    );
  }

  getPost(id: number): Observable<PostResponse> {
    return this.http.get<PostResponse>(`${this.baseUrl}get-post`, {
      params: {
        id: id.toString()
      }
    }).pipe(
      map(response => response)
    );
  }

  updatePost(postRequest: PostRequest): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}update-post`, postRequest).pipe(
      map(response => response)
    );
  }
}
