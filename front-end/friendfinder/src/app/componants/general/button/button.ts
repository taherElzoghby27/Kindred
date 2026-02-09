import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-button',
  imports: [],
  templateUrl: './button.html',
  styleUrl: './button.css',
})
export class Button {
  @Input() text: string = '';
  @Output() onClick = new EventEmitter();

  onSubmit(): void {
    this.onClick.emit();
  }
}
