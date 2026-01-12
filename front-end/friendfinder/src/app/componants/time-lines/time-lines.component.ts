import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TimeLineComponent } from './time-line/time-line.component';

@Component({
  standalone: true,
  imports: [CommonModule, TimeLineComponent],
  selector: 'app-time-lines',
  templateUrl: './time-lines.component.html',
  styleUrls: ['./time-lines.component.css']
})
export class TimeLinesComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
