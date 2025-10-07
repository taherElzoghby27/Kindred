import {Component, OnInit} from '@angular/core';
import {PostService} from '../../../../service/post/post.service';
import {PostResponse} from '../../../../model/post-response';
import {ReactionService} from '../../../../service/reaction/reaction.service';
import {ReactionRequestVm, ReactionType} from '../../../../model/reaction-request-vm';
import {GeneralResponse} from '../../../../model/general-response';
import {MatDialog} from '@angular/material/dialog';
import {DialogCommentsComponent} from '../dialog-comments/dialog-comments.component';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {


  postsResponse: GeneralResponse<PostResponse>;

  newComment = '';
  messageAr = '';
  messageEn = '';
  unKnownImage = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcgO0A7rA9MJx0DQn3Vk_kgso2c_Na-J56yA&s';

  constructor(private postService: PostService,
              private reactionService: ReactionService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.getAllPosts();
  }

  getBaseUrlForMedia(): string {
    return this.postService.baseUrlMedia;
  }

  getTimeAgo(dateTime: string): string {
    const now = new Date();
    const past = new Date(dateTime);
    const diffMs = now.getTime() - past.getTime();

    if (isNaN(past.getTime())) {
      return 'Invalid date';
    }

    const seconds = Math.floor(diffMs / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);
    const months = Math.floor(days / 30);
    const years = Math.floor(days / 365);

    if (seconds < 60) {
      return `since ${seconds} second${seconds !== 1 ? 's' : ''} ago`;
    }
    if (minutes < 60) {
      return `since ${minutes} minute${minutes !== 1 ? 's' : ''} ago`;
    }
    if (hours < 24) {
      return `since ${hours} hour${hours !== 1 ? 's' : ''} ago`;
    }
    if (days < 30) {
      return `since ${days} day${days !== 1 ? 's' : ''} ago`;
    }
    if (months < 12) {
      return `since ${months} month${months !== 1 ? 's' : ''} ago`;
    }

    return `since ${years} year${years !== 1 ? 's' : ''} ago`;
  }

  getFullMediaPost(path: string): string {
    return `${this.getBaseUrlForMedia()}${path}`;
  }

  isImage(media: string): boolean {
    return /\.(jpg|jpeg|png|gif|webp)$/i.test(media);
  }

  isVideo(media: string): boolean {
    return /\.(mp4|webm|ogg)$/i.test(media);
  }

  getAllPosts(): void {
    this.postService.getAllPosts(1, 10).subscribe(
      response => {
        this.postsResponse = response;
      },
      errors => {
        this.messageAr = errors.error.bundleMessage.message_ar;
        this.messageEn = errors.error.bundleMessage.message_en;
      }
    );
  }

  // Toggle like functionality
  toggleLike(post: PostResponse): void {
    if (post.liked) {
      this.removeReact(post.id);
    } else {
      const reactionRequest = new ReactionRequestVm(post.id, ReactionType.LIKE);
      this.makeReact(reactionRequest);
    }
  }

  makeReact(reactionRequestVm: ReactionRequestVm): void {
    this.reactionService.makeReact(reactionRequestVm).subscribe(
      response => {
        const postFounded = this.postsResponse.data.find(p => p.id === reactionRequestVm.postId);
        postFounded.liked = 1;
        postFounded.reactionsCount++;
      },
      errors => {
        this.messageAr = errors.error.bundleMessage.message_ar;
        this.messageEn = errors.error.bundleMessage.message_en;
      }
    );
  }

  removeReact(postId: number): void {
    this.reactionService.removeReact(postId).subscribe(
      response => {
        const postFounded = this.postsResponse.data.find(p => p.id === postId);
        postFounded.liked = 0;
        postFounded.reactionsCount--;
      },
      errors => {
        this.messageAr = errors.error.bundleMessage.message_ar;
        this.messageEn = errors.error.bundleMessage.message_en;
      }
    );
  }


  comments(postId: number): void {
    const dialogRef = this.dialog.open(DialogCommentsComponent, {
      width: '600px',
      data: {post_id: postId}
    });

    dialogRef.afterClosed().subscribe(result => {});
  }
}
