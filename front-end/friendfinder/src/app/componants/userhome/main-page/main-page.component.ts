import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  // Post data
  post = {
    author: 'Alexis Clark',
    authorImage: 'assets/images/users/user-5.jpg',
    timeAgo: '3 minutes ago',
    content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. 😱 😱 😱',
    image: 'assets/images/post-images/1.jpg',
    likes: 13,
    comments: 5,
    isLiked: false,
    showComments: true
  };

  // Comments data
  comments = [
    {
      author: 'Diana',
      authorImage: 'assets/images/users/user-11.jpg',
      timeAgo: '2 minutes ago',
      content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 😂'
    },
    {
      author: 'John',
      authorImage: 'assets/images/users/user-4.jpg',
      timeAgo: '1 hour ago',
      content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
    }
  ];

  newComment = '';

  constructor() {
  }

  ngOnInit(): void {
  }

  // Toggle like functionality
  toggleLike(): void {
    this.post.isLiked = !this.post.isLiked;
    this.post.likes += this.post.isLiked ? 1 : -1;
  }

  // Add new comment
  addComment(): void {
    if (this.newComment.trim()) {
      const comment = {
        author: 'You',
        authorImage: 'assets/images/users/user-1.jpg',
        timeAgo: 'now',
        content: this.newComment.trim()
      };
      this.comments.push(comment);
      this.post.comments++;
      this.newComment = '';
    }
  }

  // Toggle comments visibility
  toggleComments(): void {
    this.post.showComments = !this.post.showComments;
  }

  // Handle enter key in comment input
  onCommentKeyPress(event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      this.addComment();
    }
  }
}
