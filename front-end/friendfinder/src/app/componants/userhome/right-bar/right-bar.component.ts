import {Component, OnInit} from '@angular/core';
import {FriendshipService} from '../../../../service/friendship/friendship.service';
import {AuthService} from '../../../../service/auth/auth.service';
import {GeneralResponse} from '../../../../model/general-response';
import {AccountFriendshipVm} from '../../../../model/account-friendship-vm';
import {MatSnackBar} from '@angular/material/snack-bar';
import {SnackbarPanelClass} from '../../../../enum/snackbar-panel-class.enum';
import {FriendshipStatus} from '../../../../enum/friendship-status.enum';

@Component({
  selector: 'app-right-bar',
  templateUrl: './right-bar.component.html',
  styleUrls: ['./right-bar.component.css']
})
export class RightBarComponent implements OnInit {
  users: GeneralResponse<AccountFriendshipVm>;
  unKnownImage = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcgO0A7rA9MJx0DQn3Vk_kgso2c_Na-J56yA&s';

  constructor(
    private friendshipService: FriendshipService,
    private authService: AuthService,
    private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.authService.getUsers(1, 10).subscribe(
      result => {
        this.users = result;
      },
      errors => {
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      }
    );
  }

  requestFriend(friendId: number): void {
    this.friendshipService.requestFriend(friendId).subscribe(
      result => {
        const userFriendship = this.users.data.find(u => u.ac2_id === friendId);
        userFriendship.status = FriendshipStatus.PENDING;
      },
      errors => {
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      }
    );
  }

  currentUserId(): string {
    return this.authService.getAccountId();
  }

  removeRequestFriend(friendId: number): void {
    this.friendshipService.removeFriendShip(friendId).subscribe(
      result => {
        const userFriendship = this.users.data.find(u => u.ac2_id === friendId);
        userFriendship.status = null;
      },
      errors => {
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      }
    );
  }

  updateRequestFriend(): void {

  }

  showSnackBar(message: string, snackType: SnackbarPanelClass): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000, // milliseconds
      verticalPosition: 'bottom', // or 'top'
      panelClass: [snackType]
    });
  }

}
