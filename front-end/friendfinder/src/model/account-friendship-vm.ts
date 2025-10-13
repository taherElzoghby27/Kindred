import {FriendshipStatus} from '../enum/friendship-status.enum';
import {AccountVm} from './account-vm';

export class AccountFriendshipVm {
  account: AccountVm;
  friendId: number;
  status: FriendshipStatus;
}
