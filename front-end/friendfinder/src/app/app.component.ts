import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './componants/header/header.component';
import { RouterModule } from '@angular/router';
import { FooterComponent } from './componants/footer/footer.component';
import { AuthService } from '../service/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, HeaderComponent, RouterModule, FooterComponent],
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
