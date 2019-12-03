import {Component} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "./service/auth.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'frontend';
  authenticated = false;
  id = null;

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
    this.authenticated = false;
  }

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService,
              private http: HttpClient) {
    this.authenticate();
  }

  authenticate() {
    this.id = localStorage.getItem('id');
    if (this.id != null) {
      this.authenticated = true;
    } else {
      this.authenticated = false;
    }
  }


}


