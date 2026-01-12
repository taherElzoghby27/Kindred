import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TimeLineProfileComponent } from '../time-line-profile/time-line-profile.component';
import { TimeLineDetailesComponent } from '../time-line-detailes/time-line-detailes.component';

@Component({
  standalone: true,
  imports: [CommonModule, TimeLineProfileComponent, TimeLineDetailesComponent],
  selector: 'app-time-album',
  templateUrl: './time-album.component.html',
  styleUrls: ['./time-album.component.css']
})
export class TimeAlbumComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
