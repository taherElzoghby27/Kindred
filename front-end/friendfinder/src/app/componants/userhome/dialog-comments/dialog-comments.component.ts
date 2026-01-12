import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CommentService } from '../../../../service/comment/comment.service';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { GeneralResponse } from '../../../../model/general-response';
import { CommentResponseVm } from '../../../../model/comment-response-vm';
import { AuthService } from '../../../../service/auth/auth.service';
import { ActivatedRoute } from '@angular/router';
import { CommentRequestVm } from '../../../../model/comment-request-vm';
import { SnackbarPanelClass } from '../../../../enum/snackbar-panel-class.enum';
import { MatSnackBar } from '@angular/material/snack-bar';


// @ts-ignore
@Component({
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    InfiniteScrollModule
  ],
  selector: 'app-dialog-comments',
  templateUrl: './dialog-comments.component.html',
  styleUrls: ['./dialog-comments.component.css'],
})
export class DialogCommentsComponent implements OnInit {
  comments: GeneralResponse<CommentResponseVm>;
  unKnownImage = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcgO0A7rA9MJx0DQn3Vk_kgso2c_Na-J56yA&s';
  newComment: string;
  countComments = 0;
  // Edit state
  editingCommentId: number | null = null;
  editedContent = '';
  page = 1;
  limit = 10;

  constructor(private commentService: CommentService,
    public dialogRef: MatDialogRef<DialogCommentsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private snackBar: MatSnackBar) {
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

  onScroll(): void {
    this.page++;
    this.getAllComments();
  }

  getAllComments(): void {
    this.commentService.getComments(this.data.post_id, this.page, this.limit).subscribe(
      comments => {
        if (this.page === 1) {
          this.comments = comments;
        } else {
          this.comments.data.push(...comments.data);
        }
      },
      errors => {
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
          this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
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
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      }
    });
  }

  // Handle enter key in comment input
  onCommentKeyPress(event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      this.addComment();
    }
  }

  startEdit(comment: any): void {
    this.editingCommentId = comment.id;
    this.editedContent = comment.content;
  }

  cancelEdit(): void {
    this.editingCommentId = null;
    this.editedContent = null;
  }

  saveEdit(commentResponse: CommentResponseVm): void {
    const comment = new CommentRequestVm(
      commentResponse.content,
      commentResponse.post_id,
      commentResponse.id,
    );
    this.commentService.updateComment(comment).subscribe(
      response => {
        this.cancelEdit();
      },
      errors => {
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      },
    );
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

  showSnackBar(message: string, snackType: SnackbarPanelClass): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000, // milliseconds
      verticalPosition: 'bottom', // or 'top'
      panelClass: [snackType]
    });
  }
}
