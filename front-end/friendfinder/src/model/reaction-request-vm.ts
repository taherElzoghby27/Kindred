export enum ReactionType {
  LIKE = 'LIKE',
  LOVE = 'LOVE',
  WOW = 'WOW',
  SAD = 'SAD',
  ANGRY = 'ANGRY',
}

export class ReactionRequestVm {
  private _postId?: number;
  private _reactionType?: ReactionType;

  constructor(postId?: number, reactionType?: ReactionType) {
    this._postId = postId;
    this._reactionType = reactionType;
  }

  public get postId(): number | undefined {
    return this._postId;
  }

  public set postId(value: number | undefined) {
    this._postId = value;
  }

  public get reactionType(): ReactionType | undefined {
    return this._reactionType;
  }

  public set reactionType(value: ReactionType | undefined) {
    this._reactionType = value;
  }
}
