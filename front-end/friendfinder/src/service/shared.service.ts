import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({providedIn: 'root'})
export class SharedService {
  private messageSource = new BehaviorSubject<string>('');
  currentMessage = this.messageSource.asObservable();

  changeMessage(message: string): void {
    this.messageSource.next(message);
  }
}
