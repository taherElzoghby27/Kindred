import {Component, Inject, OnInit} from '@angular/core';
import {CommentService} from '../../../../service/comment/comment.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {GeneralResponse} from '../../../../model/general-response';
import {CommentResponseVm} from '../../../../model/comment-response-vm';
import {AuthService} from '../../../../service/auth/auth.service';
import {ActivatedRoute} from '@angular/router';
import {CommentRequestVm} from '../../../../model/comment-request-vm';


// @ts-ignore
@Component({
  selector: 'app-dialog-comments',
  templateUrl: './dialog-comments.component.html',
  styleUrls: ['./dialog-comments.component.css'],
})
export class DialogCommentsComponent implements OnInit {
  messageAr = '';
  messageEn = '';
  comments: GeneralResponse<CommentResponseVm>;
  unKnownImage = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcgO0A7rA9MJx0DQn3Vk_kgso2c_Na-J56yA&s';
  newComment: string;
  countComments = 0;

  constructor(private commentService: CommentService,
              public dialogRef: MatDialogRef<DialogCommentsComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private authService: AuthService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => this.getAllComments());
  }

  onClose(): void {
    this.dialogRef.close({
      countComments: this.countComments,
      postId: this.data.post_id,
    });
  }

  getAllComments(): void {
    this.commentService.getComments(this.data.post_id, 1, 10).subscribe(
      comments => {
        this.comments = comments;
        console.log(this.comments.data);
      },
      errors => {
        this.messageAr = errors.error.bundleMessage.message_ar;
        this.messageEn = errors.error.bundleMessage.message_en;
      }
    );
  }

// Add new comment
  addComment(): void {
    if (this.newComment.trim()) {
      const comment = new CommentRequestVm(
        this.newComment.trim(),
        this.data.post_id,
      );
      this.commentService.createComment(comment).subscribe(
        response => {
          if (this.comments == null) {
            this.comments = new GeneralResponse<CommentResponseVm>([], 1, 10);
          }
          this.comments.data.push(response);
          this.newComment = '';
          this.countComments++;
        },
        errors => {
          this.messageAr = errors.error.bundleMessage.message_ar;
          this.messageEn = errors.error.bundleMessage.message_en;
        },
      );
    }
  }

  isCurrentUserComment(comment: any): boolean {
    return comment.account?.id.toString() === this.authService.getAccountId();
  }

  confirmDelete(commentId: number): void {
    this.commentService.deleteComment(commentId).subscribe({
      next: () => {
        this.comments.data = this.comments.data.filter(
          (c: any) => c.id !== commentId
        );
        this.countComments--;
      },
      error: (errors) => {
        this.messageAr = errors.error.bundleMessage.message_ar;
        this.messageEn = errors.error.bundleMessage.message_en;
      }
    });
  }

  // Handle enter key in comment input
  onCommentKeyPress(event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      this.addComment();
    }
  }

  // Toggle comments visibility
  toggleComments(): void {
    // this.post.showComments = !this.post.showComments;
  }

  getTimeAgo(input: string | Date): string {
    // Convert to Date object if it's a string
    const date = typeof input === 'string' ? new Date(input) : input;
    const now = new Date();

    const seconds = Math.floor((now.getTime() - date.getTime()) / 1000);

    if (seconds < 60) {
      return 'Just now';
    }
    if (seconds < 3600) {
      return Math.floor(seconds / 60) + 'm ago';
    }
    if (seconds < 86400) {
      return Math.floor(seconds / 3600) + 'h ago';
    }
    if (seconds < 604800) {
      return Math.floor(seconds / 86400) + 'd ago';
    }
    if (seconds < 2592000) {
      return Math.floor(seconds / 604800) + 'w ago';
    }
    if (seconds < 31536000) {
      return Math.floor(seconds / 2592000) + 'mo ago';
    }

    return Math.floor(seconds / 31536000) + 'y ago';
  }

  getCurrentUser(): string {
    return this.authService.getCurrentUser();
  }
}
