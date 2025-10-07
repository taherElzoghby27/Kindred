export class CommentResponseVm {
  id!: number;
  content!: string;
  post_id!: number;
  createdBy!: string;
  updatedDate!: Date;
  createdDate!: Date;

  constructor(
    id: number,
    content: string,
    post_id: number,
    createdBy: string,
    updatedDate: Date,
    createdDate: Date
  ) {
    this.id = id;
    this.content = content;
    this.post_id = post_id;
    this.createdBy = createdBy;
    this.updatedDate = updatedDate;
    this.createdDate = createdDate;
  }

  public getId(): number {
    return this.id;
  }

  public setId(value: number): void {
    this.id = value;
  }

  public getContent(): string {
    return this.content;
  }

  public setContent(value: string): void {
    this.content = value;
  }

  public getPostId(): number {
    return this.post_id;
  }

  public setPostId(value: number): void {
    this.post_id = value;
  }

  public getCreatedBy(): string {
    return this.createdBy;
  }

  public setCreatedBy(value: string): void {
    this.createdBy = value;
  }

  public getUpdatedDate(): Date {
    return this.updatedDate;
  }

  public setUpdatedDate(value: Date): void {
    this.updatedDate = value;
  }

  public getCreatedDate(): Date {
    return this.createdDate;
  }

  public setCreatedDate(value: Date): void {
    this.createdDate = value;
  }
}
