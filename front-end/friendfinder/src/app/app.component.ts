import {Component, OnInit} from '@angular/core';
import {AuthService} from '../service/auth.service';
import {Router} from '@angular/router';

@Component({
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
