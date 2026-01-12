import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TimeLineProfileComponent } from '../time-line-profile/time-line-profile.component';
import { TimeLineDetailesComponent } from '../time-line-detailes/time-line-detailes.component';

@Component({
  standalone: true,
  imports: [CommonModule, TimeLineProfileComponent, TimeLineDetailesComponent],
  selector: 'app-time-about',
  templateUrl: './time-about.component.html',
  styleUrls: ['./time-about.component.css']
})
export class TimeAboutComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
