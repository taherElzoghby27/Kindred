import { AccountVm } from './account-vm';
import {PostResponse} from "./post-response";

export class CommentResponseVm {
  id?: number;
  content?: string;
  post?: PostResponse;
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
    post?: PostResponse,
    createdBy?: string,
    updatedDate?: Date,
    createdDate?: Date,
    account?: AccountVm,
  ) {
    this.id = id;
    this.content = content;
    this.post = post;
    this.createdBy = createdBy;
    this.updatedDate = updatedDate;
    this.createdDate = createdDate;
    this.account = account;
  }
}
