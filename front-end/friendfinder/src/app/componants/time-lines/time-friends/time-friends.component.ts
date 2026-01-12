import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TimeLineProfileComponent } from '../time-line-profile/time-line-profile.component';
import { TimeLineDetailesComponent } from '../time-line-detailes/time-line-detailes.component';

@Component({
  standalone: true,
  imports: [CommonModule, TimeLineProfileComponent, TimeLineDetailesComponent],
  selector: 'app-time-friends',
  templateUrl: './time-friends.component.html',
  styleUrls: ['./time-friends.component.css']
})
export class TimeFriendsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
