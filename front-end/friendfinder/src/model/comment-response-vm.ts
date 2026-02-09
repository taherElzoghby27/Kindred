import { AccountVm } from './account-vm';

export class CommentResponseVm {
  id?: number;
  content?: string;
  postId?: number;
  createdBy?: string;
  updatedDate?: Date;
  createdDate?: Date;
  account?: AccountVm;

  // Pre-calculated fields for performance
  timeAgo?: string;
  isMine?: boolean;

  constructor(
    id?: number,
    content?: string,
    postId?: number,
    createdBy?: string,
    updatedDate?: Date,
    createdDate?: Date,
    account?: AccountVm,
  ) {
    this.id = id;
    this.content = content;
    this.postId = postId;
    this.createdBy = createdBy;
    this.updatedDate = updatedDate;
    this.createdDate = createdDate;
    this.account = account;
  }
}
