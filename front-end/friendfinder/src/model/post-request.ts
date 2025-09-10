export class PostRequest {
  id: number | null;
  /** Content of the post */
  content: string;

  /** Media file (video or photo) */
  media?: File;
}
