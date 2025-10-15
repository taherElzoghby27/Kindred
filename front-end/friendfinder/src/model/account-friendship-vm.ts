import {FriendshipStatus} from '../enum/friendship-status.enum';

export class AccountFriendshipVm {
  ac1_id: number;
  ac1_username: string;
  ac2_id: number;
  ac2_username: string;
  friendship_id: number | null;
  status: FriendshipStatus;
}
