import {AccountVm} from './account-vm';

export class CommentResponseVm {
  id!: number;
  content!: string;
  post_id!: number;
  createdBy!: string;
  updatedDate!: Date;
  createdDate!: Date;
  account?: AccountVm;

  constructor(
    id: number,
    content: string,
    post_id: number,
    createdBy: string,
    updatedDate: Date,
    createdDate: Date,
    account?: AccountVm,
  ) {
    this.id = id;
    this.content = content;
    this.post_id = post_id;
    this.createdBy = createdBy;
    this.updatedDate = updatedDate;
    this.createdDate = createdDate;
    this.account = account;
  }
}
