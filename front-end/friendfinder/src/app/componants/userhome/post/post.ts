import {Component, EventEmitter, Input, Output, ViewChild, ElementRef, HostListener} from '@angular/core';
import {PostResponse} from 'src/model/post-response';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-post',
  imports: [
    FormsModule
  ],
  templateUrl: './post.html',
  styleUrl: './post.css',
})
export class Post {
  @Input() post?: PostResponse;
  @Output() onEditPostClick = new EventEmitter<PostResponse>();
  @Output() onDeletePostClick = new EventEmitter<PostResponse>();
  @Output() closeDropdownClick = new EventEmitter();
  @Output() savePostEditClick = new EventEmitter<PostResponse>();
  @Output() toggleLikeClick = new EventEmitter<PostResponse>();
  @Output() commentsClick = new EventEmitter<number>();
  unKnownImage = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcgO0A7rA9MJx0DQn3Vk_kgso2c_Na-J56yA&s';
  @Input() edit = false;
  @Input() editId: number = -1;

  @ViewChild('dropdown') dropdown!: ElementRef<HTMLDivElement>;

  @HostListener('window:scroll')
  @HostListener('click', ['$event'])
  onScrollOrClick(event?: Event) {
    // Close dropdown when scrolling or clicking outside
    if (this.dropdown && !this.dropdown.nativeElement.hidden) {
      // Check if click is outside dropdown
      if (event && event.target) {
        const clickedElement = event.target as HTMLElement;
        const dropdownElement = this.dropdown.nativeElement;

        if (!dropdownElement.contains(clickedElement) &&
          !clickedElement.closest('.btn-options')) {
          this.closeDropdown();
        }
      } else if (!event) {
        // Scroll event
        this.closeDropdown();
      }
    }
  }

  onEditPostC(post: PostResponse): void {
    this.closeDropdown();
    this.onEditPostClick.emit(post);
  }

  onDeletePostC(post: PostResponse): void {
    this.closeDropdown();
    this.onDeletePostClick.emit(post);
  }

  closeDropdownC(): void {
    this.closeDropdownClick.emit();
  }

  toggleDropdown(event: Event): void {
    const button = event.target as HTMLElement;
    const rect = button.getBoundingClientRect();

    if (this.dropdown.nativeElement.hidden) {
      const dropdownEl = this.dropdown.nativeElement;
      dropdownEl.style.top = `${rect.bottom + window.scrollY}px`;
      dropdownEl.style.left = `${rect.right - 100 + window.scrollX}px`;
      dropdownEl.hidden = false;
    } else {
      this.dropdown.nativeElement.hidden = true;
    }
  }

  closeDropdown(): void {
    this.dropdown.nativeElement.hidden = true;
  }

  savePostEditC(post: PostResponse): void {
    this.savePostEditClick.emit(post);
  }

  toggleLikeC(post: PostResponse): void {
    this.toggleLikeClick.emit(post);
  }

  commentsC(postId: number): void {
    this.commentsClick.emit(postId);
  }
}
