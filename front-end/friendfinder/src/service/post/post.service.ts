import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PostRequest } from '../../model/post-request';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { PostResponse } from '../../model/post-response';
import { GeneralResponse } from '../../model/general-response';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  baseUrl = 'http://localhost:7070/posts';
  baseUrlMedia = 'assets/images/post-images/';

  constructor(private http: HttpClient) {
  }

  createPost(postRequest: PostRequest): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}`, postRequest).pipe(
      map(response => response)
    );
  }

  getAllPosts(page: number, pageSize: number): Observable<GeneralResponse<PostResponse>> {
    return this.http.get<any>(`${this.baseUrl}/all-posts`, {
      params: {
        page: page.toString(),
        page_size: pageSize.toString(),
      }
    }).pipe(
      map((response: any) => response.data.body)
    );
  }

  searchByContent(page: number, pageSize: number, content: string): Observable<GeneralResponse<PostResponse>> {
    return this.http.get<any>(`${this.baseUrl}/posts-with-content`, {
      params: {
        page: page.toString(),
        page_size: pageSize.toString(),
        content: content.toString(),
      }
    }).pipe(
      map((response: any) => response.data.body)
    );
  }

  getMyPosts(page: number, pageSize: number): Observable<GeneralResponse<PostResponse>> {
    return this.http.get<any>(`${this.baseUrl}/my-posts`, {
      params: {
        page: page.toString(),
        page_size: pageSize.toString(),
      }
    }).pipe(
      map((response: any) => response.data.body)
    );
  }

  deletePost(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}`, {
      params: {
        id: id.toString()
      }
    }).pipe(
      map(response => response)
    );
  }

  getPost(id: number): Observable<PostResponse> {
    return this.http.get<PostResponse>(`${this.baseUrl}/${id}`).pipe(
      map(response => response)
    );
  }

  updatePost(postRequest: PostRequest): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}`, postRequest).pipe(
      map(response => response)
    );
  }
}
