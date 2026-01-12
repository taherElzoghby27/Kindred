import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { PostService } from '../../../../service/post/post.service';
import { PostResponse } from '../../../../model/post-response';
import { ReactionService } from '../../../../service/reaction/reaction.service';
import { ReactionRequestVm, ReactionType } from '../../../../model/reaction-request-vm';
import { GeneralResponse } from '../../../../model/general-response';
import { MatDialog } from '@angular/material/dialog';
import { DialogCommentsComponent } from '../dialog-comments/dialog-comments.component';
import { PostRequest } from '../../../../model/post-request';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarPanelClass } from '../../../../enum/snackbar-panel-class.enum';
import { AuthService } from '../../../../service/auth/auth.service';
import { SharedService } from '../../../../service/shared.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LeftBarComponent } from '../left-bar/left-bar.component';
import { RightBarComponent } from '../right-bar/right-bar.component';
import { PublishComponent } from '../publish/publish.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { Subscription } from 'rxjs';

@Component({
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    LeftBarComponent,
    RightBarComponent,
    PublishComponent,
    InfiniteScrollModule
  ],
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit, OnDestroy {


  postsResponse: GeneralResponse<PostResponse>;
  isDropdownOpen = false;
  unKnownImage = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcgO0A7rA9MJx0DQn3Vk_kgso2c_Na-J56yA&s';
  edit = false;
  editId: number;
  page = 1;
  limit = 10;
  dropdownTop = 0;
  dropdownRight = 0;
  private messageSubscription: Subscription;

  constructor(private postService: PostService,
    private reactionService: ReactionService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar,
    private authService: AuthService,
    private sharedService: SharedService,
    private cdr: ChangeDetectorRef
  ) {
  }

  ngOnInit(): void {
    this.messageSubscription = this.sharedService.currentMessage.subscribe(msg => {
      if (msg === '') {
        this.getAllPosts();
      } else {
        this.searchByContent(msg);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
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
    this.postService.updatePost(post).subscribe({
      next: success => {
        this.processPost(postResponse);
        this.closeDropdown();
        this.showSnackBar('Updated', SnackbarPanelClass.Success);
      }, error: errors => {
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      }
    });
  }

  onDeletePost(post: PostResponse): void {
    this.postService.deletePost(post.id).subscribe({
      next: success => {
        this.removePostLocal(post.id);
        this.closeDropdown();
        this.showSnackBar('Deleted', SnackbarPanelClass.Success);
      }, error: errors => {
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      }
    });
  }

  removePostLocal(postId: number): void {
    this.postsResponse.data = this.postsResponse.data.filter(p => p.id !== postId);
  }

  onScroll(): void {
    this.page++;
    this.getAllPosts();
  }

  isCurrentUserPost(post: PostResponse): boolean {
    return post.account?.id.toString() === this.authService.getAccountId();
  }

  getAllPosts(): void {
    console.log('MainPage: Fetching all posts...');
    this.postService.getAllPosts(this.page, this.limit).subscribe({
      next: response => {
        console.log('MainPage: Posts received:', response.data.length);
        response.data.forEach(post => this.processPost(post));
        if (this.page === 1) {
          this.postsResponse = response;
        } else {
          this.postsResponse.data.push(...response.data);
        }
        this.cdr.detectChanges();
      },
      error: errors => {
        console.error('MainPage: Error fetching posts:', errors);
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      }
    });
  }

  searchByContent(content: string): void {
    console.log('MainPage: Searching for:', content);
    this.postService.searchByContent(this.page, this.limit, content).subscribe({
      next: response => {
        console.log('MainPage: Search results received:', response.data.length);
        response.data.forEach(post => this.processPost(post));
        if (this.page === 1) {
          this.postsResponse = response;
        } else {
          this.postsResponse.data.push(...response.data);
        }
        this.cdr.detectChanges();
      },
      error: errors => {
        console.error('MainPage: Error searching posts:', errors);
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      }
    });
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
    this.reactionService.makeReact(reactionRequestVm).subscribe({
      next: response => {
        const postFounded = this.postsResponse.data.find(p => p.id === reactionRequestVm.postId);
        postFounded.liked = 1;
        postFounded.reactionsCount++;
      },
      error: errors => {
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      }
    });
  }

  removeReact(postId: number): void {
    this.reactionService.removeReact(postId).subscribe({
      next: response => {
        const postFounded = this.postsResponse.data.find(p => p.id === postId);
        postFounded.liked = 0;
        postFounded.reactionsCount--;
      },
      error: errors => {
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      }
    });
  }


  comments(postId: number): void {
    const dialogRef = this.dialog.open(DialogCommentsComponent, {
      width: '600px',
      data: { post_id: postId }
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

  processPost(post: PostResponse): void {
    if (!post) return;
    post.timeAgo = this.getTimeAgo(post.createdDate);
    post.isImage = post.media ? this.isImage(post.media) : false;
    post.isVideo = post.media ? this.isVideo(post.media) : false;
    post.isMine = this.isCurrentUserPost(post);
    post.fullMediaUrl = post.media ? this.getFullMediaPost(post.media) : null;
  }

  trackByPost(index: number, post: PostResponse): number {
    return post.id;
  }
}
