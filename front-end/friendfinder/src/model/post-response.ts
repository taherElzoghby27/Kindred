import { AccountVm } from './account-vm';

export class PostResponse {
  id: number;
  content: string;
  media?: string;
  reactionsCount: number;
  commentsCount: number;
  liked: number;
  createdDate: string;
  account: AccountVm;
  // Pre-calculated fields for performance
  timeAgo?: string;
  isImage?: boolean;
  isVideo?: boolean;
  isMine?: boolean;
  fullMediaUrl?: string;
}
