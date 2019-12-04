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
  principal = null;
  authenticated = false;
  admin = null;

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
    this.authenticated = false;
  }

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService,
              private http: HttpClient) {
    this.authenticate();
    this.role();
  }

  role() {
    this.authService.role().subscribe(data => {
      this.admin = this.authenticated && data['roles'] && data['roles'].indexOf('VIEWER') > -1;
    });
  }

  authenticate() {
    this.principal = this.authService.principal()
    if (this.principal != null) {
      this.authenticated = true;
    } else this.authenticated = false;
  }


}


