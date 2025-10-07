import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {GeneralResponse} from '../../model/general-response';
import {PostResponse} from '../../model/post-response';
import {CommentRequestVm} from '../../model/comment-request-vm';
import {CommentResponseVm} from '../../model/comment-response-vm';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  baseUrl = ' http://localhost:7070/comments/';

  constructor(private http: HttpClient) {
  }

  createComment(commentRequestVm: CommentRequestVm): Observable<CommentResponseVm> {
    const body = {
      content: commentRequestVm.content,
      post_id: commentRequestVm.postId,
    };
    return this.http.post<any>(`${this.baseUrl}create-comment`, body).pipe(
      map(response => response.data.body)
    );
  }

  getComments(postId: number, page: number, pageSize: number): Observable<GeneralResponse<CommentResponseVm>> {
    return this.http.get<any>(`${this.baseUrl}get-comments`, {
      params: {
        page: page.toString(),
        page_size: pageSize.toString(),
        post_id: postId.toString()
      }
    }).pipe(
      map(response => response.data.body)
    );
  }

  deleteComment(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}delete-comment`, {
      params: {
        comment_id: id.toString()
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

  updateComment(commentRequestVm: CommentRequestVm): Observable<any> {
    const body = {
      id: commentRequestVm.id,
      content: commentRequestVm.content,
      post_id: commentRequestVm.postId,
    };
    return this.http.put<any>(`${this.baseUrl}update-comment`, body).pipe(
      map(response => response)
    );
  }
}
