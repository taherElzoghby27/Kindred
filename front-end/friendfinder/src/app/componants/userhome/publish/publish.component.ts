import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PostService } from '../../../../service/post/post.service';
import { PostRequest } from '../../../../model/post-request';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarPanelClass } from '../../../../enum/snackbar-panel-class.enum';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  selector: 'app-publish',
  templateUrl: './publish.component.html',
  styleUrls: ['./publish.component.css']
})
export class PublishComponent {
  @ViewChild('mediaInput') mediaInput!: ElementRef<HTMLInputElement>;

  selectedFileName: string | null = null;
  file: File;
  previewUrl: string | null = null;
  isImage = false;
  loading = false;
  content: string;
  errorMessage = '';

  constructor(private postService: PostService, private snackBar: MatSnackBar) {
  }

  // create post (publish)
  publish(): void {
    if (!this.validateFields()) {
      return;
    }
    const post = new PostRequest(null, this.content, this.file == null ? '' : this.file.name);
    this.postService.createPost(post).subscribe({
      next: success => {
        this.showSnackBar('Published', SnackbarPanelClass.Success);
        this.clearData();
      }, error: errors => {
        this.showSnackBar(errors.error.bundleMessage.message_en, SnackbarPanelClass.Error);
      }
    });
  }

  validateFields(): boolean {
    if (!this.selectedFileName && !this.content) {
      this.errorMessage = 'One Required';
      return false;
    }
    return true;
  }

  clearData(): void {
    this.errorMessage = '';
    this.selectedFileName = null;
    this.content = null;
    this.previewUrl = null;
    this.file = null;
  }

  onMediaClick(): void {
    this.mediaInput.nativeElement.click();
  }

  onMediaSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.file = input.files[0];
      this.selectedFileName = this.file.name;
      this.loading = true;
      this.previewImage(this.file);
    }
  }

  previewImage(file: File): void {
    // Preview (local only)
    const reader = new FileReader();
    reader.onload = () => {
      this.previewUrl = reader.result as string;
      this.isImage = file.type.startsWith('image/');
      this.loading = false;
    };
    reader.readAsDataURL(file);
  }

  showSnackBar(message: string, snackType: SnackbarPanelClass): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000, // milliseconds
      verticalPosition: 'bottom', // or 'top'
      panelClass: [snackType]
    });
  }
}
