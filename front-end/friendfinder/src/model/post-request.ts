export class PostRequest {
  id: number | null;
  content?: string;
  media?: string;

  constructor(
    id: number | null = null,
    content?: string,
    media?: string
  ) {
    this.id = id;
    this.content = content;
    this.media = media;
  }
}
