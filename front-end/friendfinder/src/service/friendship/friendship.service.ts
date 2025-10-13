import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {FriendshipStatus} from '../../enum/friendship-status.enum';

@Injectable({
  providedIn: 'root'
})
export class FriendshipService {
  baseUrl = 'http://localhost:7070/friendship/';

  constructor(private http: HttpClient) {
  }

  requestFriend(friendId: number): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}create-friendship`, {
      params: {
        friend_id: friendId.toString(),
      }
    }).pipe(
      map(response => response)
    );
  }

  updateFriendShip(friendshipId: number, status: FriendshipStatus): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}update-friendship`, {
      params: {
        friendship_id: friendshipId.toString(),
        status: status.toString(),
      }
    }).pipe(
      map(response => response)
    );
  }

  removeFriendShip(friendshipId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}remove-friendship`, {
      params: {
        friendship_id: friendshipId.toString(),
      }
    }).pipe(
      map(response => response)
    );
  }
}
