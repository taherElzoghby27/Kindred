import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LeftBarComponent } from '../left-bar/left-bar.component';
import { PublishComponent } from '../publish/publish.component';
import { RightBarComponent } from '../right-bar/right-bar.component';

@Component({
  standalone: true,
  imports: [CommonModule, LeftBarComponent, PublishComponent, RightBarComponent],
  selector: 'app-cool-images',
  templateUrl: './cool-images.component.html',
  styleUrls: ['./cool-images.component.css']
})
export class CoolImagesComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
