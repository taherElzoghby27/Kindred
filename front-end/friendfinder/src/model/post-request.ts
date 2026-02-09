export class PostRequest {
  id?: number;
  content?: string;
  media?: string;

  constructor(
    id?: number,
    content?: string,
    media?: string
  ) {
    this.id = id;
    this.content = content;
    this.media = media;
  }
}
