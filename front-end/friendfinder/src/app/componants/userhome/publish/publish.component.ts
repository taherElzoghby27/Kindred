import {Component, ElementRef, ViewChild} from '@angular/core';

@Component({
  selector: 'app-publish',
  templateUrl: './publish.component.html',
  styleUrls: ['./publish.component.css']
})
export class PublishComponent {
  @ViewChild('mediaInput') mediaInput!: ElementRef<HTMLInputElement>;

  selectedFileName: string | null = null;
  previewUrl: string | null = null;
  isImage = false;
  loading = false;

  constructor() {
  }

  onMediaClick(): void {
    this.mediaInput.nativeElement.click();
  }

  onMediaSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.selectedFileName = file.name;
      console.log('Selected file:', file.name);

      this.loading = true;

      // Preview (local only)
      const reader = new FileReader();
      reader.onload = () => {
        this.previewUrl = reader.result as string;
        this.isImage = file.type.startsWith('image/');
        this.loading = false;
      };
      reader.readAsDataURL(file);

      // Upload to backend
      // const formData = new FormData();
      // formData.append('file', file);
      //
      // const formData = new FormData();
      // formData.append('content', this.postText);
      // formData.append('media', this.selectedFile);
      //
      // this.http.post('http://localhost:8080/posts', formData).subscribe();

      // this.http.post('http://localhost:3000/upload', formData).subscribe({
      //   next: (res: any) => {
      //     console.log('File saved successfully:', res.fileName);
      //     // here you can save res.fileName in your database
      //   },
      //   error: (err) => {
      //     console.error('Upload error:', err);
      //   }
      // });
    }
  }
}
