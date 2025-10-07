export class CommentRequestVm {
  id?: number;
  content: string;
  postId: number;

  constructor(content: string, postId: number, id?: number) {
    this.id = id;
    this.content = content;
    this.postId = postId;
  }
}
