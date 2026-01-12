import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TimeLineProfileComponent } from '../time-line-profile/time-line-profile.component';
import { PublishComponent } from '../../userhome/publish/publish.component';
import { TimeLineDetailesComponent } from '../time-line-detailes/time-line-detailes.component';

@Component({
  standalone: true,
  imports: [CommonModule, TimeLineProfileComponent, PublishComponent, TimeLineDetailesComponent],
  selector: 'app-time-line',
  templateUrl: './time-line.component.html',
  styleUrls: ['./time-line.component.css']
})
export class TimeLineComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
