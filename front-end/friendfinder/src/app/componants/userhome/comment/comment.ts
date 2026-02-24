import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatIcon } from "@angular/material/icon";
import { MatIconButton } from "@angular/material/button";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { CommentResponseVm } from "../../../../model/comment-response-vm";
import {NgOptimizedImage} from "@angular/common";

@Component({
  selector: 'app-comment',
  imports: [
    MatIcon,
    MatIconButton,
    ReactiveFormsModule,
    FormsModule,
    NgOptimizedImage
  ],
  templateUrl: './comment.html',
  styleUrl: './comment.css',
})
export class Comment {
  @Input() comment: CommentResponseVm;
  @Input() unKnownImage: String;
  @Input() currentUser: String;
  @Input() timeAgo: string;
  @Input() isCurrentUserComment: boolean;
  @Output() startEdit: EventEmitter<CommentResponseVm> = new EventEmitter();
  @Output() cancelEdit: EventEmitter<any> = new EventEmitter();
  @Input() editingCommentId: number;
  @Output() confirmDelete: EventEmitter<number> = new EventEmitter();
  @Output() saveEdit: EventEmitter<CommentResponseVm> = new EventEmitter();

  startEditClick(comment: CommentResponseVm): void {
    this.startEdit.emit(comment);
  }

  cancelEditClick(): void {
    this.cancelEdit.emit();
  }

  confirmDeleteClick(commentId: number): void {
    this.confirmDelete.emit(commentId);
  }

  saveEditClick(commentResponse: CommentResponseVm): void {
    this.saveEdit.emit(commentResponse);
  }
}
