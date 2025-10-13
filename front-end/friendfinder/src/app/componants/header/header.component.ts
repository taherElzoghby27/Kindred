import {Component, OnInit} from '@angular/core';
import {SharedService} from '../../../service/shared.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private sharedService: SharedService) {
  }

  ngOnInit(): void {
  }

  searchPosts(content: string): void {
    this.sharedService.changeMessage(content);
  }
}
