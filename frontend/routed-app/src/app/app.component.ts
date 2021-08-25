import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  // here we can declare our data models
  title = 'routed-app';
  today = new Date()
  user = 'Ada'
  askCookieConsent = true
  fbImage = 'KenojuakAshevak'

  // In ES6 functions that belong to classes are written like this
  agreeCookie(){
    this.askCookieConsent = false
  }


}
