import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ReactionRequestVm} from '../../model/reaction-request-vm';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ReactionService {
  baseUrl = 'http://localhost:7070/reaction/';

  constructor(private http: HttpClient) {
  }

  makeReact(reactionRequestVm: ReactionRequestVm): Observable<any> {

    const body = {
      post_id: reactionRequestVm.postId, reaction_type: reactionRequestVm.reactionType
    };
    return this.http.post<any>(`${this.baseUrl}reaction-request`, body).pipe(
      map(response => response)
    );
  }

  removeReact(postId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}delete-react`, {
      params: {
        post_id: postId.toString()
      }
    }).pipe(
      map(response => response)
    );
  }
}
