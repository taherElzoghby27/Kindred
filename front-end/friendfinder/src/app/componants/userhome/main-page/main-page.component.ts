import {Component, OnInit} from '@angular/core';
import {PostService} from '../../../../service/post/post.service';
import {PostResponse} from '../../../../model/post-response';
import {ReactionService} from '../../../../service/reaction/reaction.service';
import {ReactionRequestVm, ReactionType} from '../../../../model/reaction-request-vm';
import {GeneralResponse} from '../../../../model/general-response';
import {MatDialog} from '@angular/material/dialog';
import {DialogCommentsComponent} from '../dialog-comments/dialog-comments.component';
import {PostRequest} from '../../../../model/post-request';
import {MatSnackBar} from '@angular/material/snack-bar';
import {SnackbarPanelClass} from '../../../../enum/snackbar-panel-class.enum';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {


  postsResponse: GeneralResponse<PostResponse>;
  isDropdownOpen = false;
  unKnownImage = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcgO0A7rA9MJx0DQn3Vk_kgso2c_Na-J56yA&s';
  edit = false;
  editId: number;
  page = 1;
  limit = 10;
  loading = false;

  constructor(private postService: PostService,
              private reactionService: ReactionService,
              public dialog: MatDialog, private snackBar: MatSnackBar
  ) {
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

  closeDropdown(): void {
    this.edit = false;
    this.isDropdownOpen = false;
  }

  onEditPost(post: PostResponse): void {
    this.edit = true;
    this.editId = post.id;
  }

  savePostEdit(postResponse: PostResponse): void {
    const post = new PostRequest(postResponse.id, postResponse.content, postResponse.media);
    this.postService.updatePost(post).subscribe(
      success => {
        this.closeDropdown();
        this.showSnackBar('Updated', SnackbarPanelClass.Success);
      }, errors => {
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      });
  }

  onDeletePost(post: PostResponse): void {
    this.postService.deletePost(post.id).subscribe(
      success => {
        this.removePostLocal(post.id);
        this.closeDropdown();
        this.showSnackBar('Deleted', SnackbarPanelClass.Success);
      }, errors => {
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      });
  }

  removePostLocal(postId: number): void {
    this.postsResponse.data = this.postsResponse.data.filter(p => p.id !== postId);
  }

  onScroll(): void {
    this.page++;
    this.getAllPosts();
  }

  getAllPosts(): void {
    if (this.loading) {
      return;
    }
    this.loading = true;
    this.postService.getAllPosts(this.page, this.limit).subscribe(
      response => {
        this.loading = false;
        if (this.page === 1) {
          this.postsResponse = response;
        } else {
          this.postsResponse.data.push(...response.data);
        }
      },
      errors => {
        this.loading = false;
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
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
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
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
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      }
    );
  }


  comments(postId: number): void {
    const dialogRef = this.dialog.open(DialogCommentsComponent, {
      width: '600px',
      data: {post_id: postId}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Name:', result.countComments);
        const postFounded = this.postsResponse.data.find(p => p.id === result.postId);
        postFounded.commentsCount += result.countComments;
      }
    });
  }

  showSnackBar(message: string, snackType: SnackbarPanelClass): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000, // milliseconds
      verticalPosition: 'bottom', // or 'top'
      panelClass: [snackType]
    });
  }
}
