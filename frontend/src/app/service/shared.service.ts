import {Injectable, Output, EventEmitter} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  @Output() fire: EventEmitter<any> = new EventEmitter();

  constructor() {
    console.log('shared service started');
  }

  ru() {
    console.log('change started');
    this.fire.emit(true);
  }

  en() {
    console.log('change started');
    this.fire.emit(false);
  }


  getEmittedValue() {
    return this.fire;
  }

}
