import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  imports: [CommonModule],
  selector: 'app-time-line-detailes',
  templateUrl: './time-line-detailes.component.html',
  styleUrls: ['./time-line-detailes.component.css']
})
export class TimeLineDetailesComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
