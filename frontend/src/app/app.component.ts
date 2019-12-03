import {Component} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "./service/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService) {
  }
}
