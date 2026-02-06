import { Component, Input } from '@angular/core';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-auth-redirect',
  imports: [RouterLink],
  templateUrl: './auth-redirect.html',
  styleUrl: './auth-redirect.css',
})
export class AuthRedirect {
  @Input() text: string = '';
  @Input() textButton: string = '';
  @Input() textOnClick: string = '';
}
