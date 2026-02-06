import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { AuthService } from '../service/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private authService: AuthService, private router: Router) {
  }

  // isProducts = false;
  //
  // isLogin(): boolean {
  //   return this.authService.isLogin();
  // }
  //
  // ngOnInit(): void {
  //   this.router.events.subscribe(() => {
  //     const url = this.router.url;
  //     if (url.includes('/products') || url.includes('/category')) {
  //       this.isProducts = true;
  //     } else {
  //       this.isProducts = false;
  //     }
  //   });
  // }
}
