import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LeftBarComponent } from '../left-bar/left-bar.component';
import { PublishComponent } from '../publish/publish.component';
import { RightBarComponent } from '../right-bar/right-bar.component';

@Component({
  standalone: true,
  imports: [CommonModule, LeftBarComponent, PublishComponent, RightBarComponent],
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
