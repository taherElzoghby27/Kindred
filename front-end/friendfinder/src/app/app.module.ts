import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {HeaderComponent} from './componants/header/header.component';
import {FooterComponent} from './componants/footer/footer.component';
import {SignUpComponent} from './componants/sign-up/sign-up.component';
import {ContactComponent} from './componants/contact/contact.component';
import {UserhomeComponent} from './componants/userhome/userhome.component';
import {LeftBarComponent} from './componants/userhome/left-bar/left-bar.component';
import {RightBarComponent} from './componants/userhome/right-bar/right-bar.component';
import {PublishComponent} from './componants/userhome/publish/publish.component';
import {FriendsComponent} from './componants/userhome/friends/friends.component';
import {CoolImagesComponent} from './componants/userhome/cool-images/cool-images.component';
import {MainPageComponent} from './componants/userhome/main-page/main-page.component';
import {TimeLinesComponent} from './componants/time-lines/time-lines.component';
import {TimeLineComponent} from './componants/time-lines/time-line/time-line.component';
import {TimeAboutComponent} from './componants/time-lines/time-about/time-about.component';
import {TimeAlbumComponent} from './componants/time-lines/time-album/time-album.component';
import {TimeFriendsComponent} from './componants/time-lines/time-friends/time-friends.component';
import {TimeLineProfileComponent} from './componants/time-lines/time-line-profile/time-line-profile.component';
import {TimeLineDetailesComponent} from './componants/time-lines/time-line-detailes/time-line-detailes.component';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './componants/login/login.component';
import {FormsModule} from '@angular/forms';
import {LoginSignupGuard} from '../service/activitor/login-signup.guard';
import {AuthGuard} from '../service/activitor/auth.guard';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {APP_BASE_HREF} from '@angular/common';
import {AuthInterceptor} from '../service/interceptors/auth.interceptor';

// http://localhost:4200

const routes: Routes = [
  {path: 'sign-up', component: SignUpComponent, canActivate: [LoginSignupGuard]},
  {path: 'login', component: LoginComponent, canActivate: [LoginSignupGuard]},
  {path: 'mainpage', component: MainPageComponent, canActivate: [AuthGuard]},
  {path: 'contact', component: ContactComponent, canActivate: [AuthGuard]},
  {path: 'timeline', component: TimeLineComponent, canActivate: [AuthGuard]},
  {path: 'timeline-about', component: TimeAboutComponent, canActivate: [AuthGuard]},
  {path: 'timeline-friends', component: TimeFriendsComponent, canActivate: [AuthGuard]},
  {path: 'timeline-album', component: TimeAlbumComponent, canActivate: [AuthGuard]},
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: '**', redirectTo: '/login', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SignUpComponent,
    ContactComponent,
    UserhomeComponent,
    LeftBarComponent,
    RightBarComponent,
    PublishComponent,
    FriendsComponent,
    CoolImagesComponent,
    MainPageComponent,
    TimeLinesComponent,
    TimeLineComponent,
    TimeAboutComponent,
    TimeAlbumComponent,
    TimeFriendsComponent,
    TimeLineProfileComponent,
    TimeLineDetailesComponent,
    LoginComponent,
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    FormsModule,
    NoopAnimationsModule
  ],
  providers: [
    {provide: APP_BASE_HREF, useValue: '/'},
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
