export class PostRequest {
  id: number | null;
  content?: string;
  media?: File;

  constructor(
    id: number | null = null,
    content?: string,
    media?: File
  ) {
    this.id = id;
    this.content = content;
    this.media = media;
  }
}
