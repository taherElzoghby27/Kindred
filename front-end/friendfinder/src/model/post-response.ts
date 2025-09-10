import {AccountVm} from './account-vm';

export class PostResponse {
  id: number;
  content: string;
  media?: string;
  reactionsCount: number;
  commentsCount: number;
  createdDate: string;
  account: AccountVm;
}
