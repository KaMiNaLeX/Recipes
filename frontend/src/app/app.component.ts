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
  admin = false;
  author = false;

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
    this.authenticated = false;
    this.admin = false;
    this.author = false;
  }

  addRecipe() {
    if (this.author != false || this.admin != false) {
      this.router.navigate(['addRecipe']);
    } else {
      window.alert("You need have AUTHOR or ADMIN role")
    }

  }

  categories() {
    this.router.navigate(['category']);
  }

  register() {
    this.router.navigate(['register']);
  }

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService,
              private http: HttpClient) {
    this.authenticate();
    this.role();
  }

  role() {
    this.authService.role().subscribe(data => {
      this.admin = (this.authenticated && data['roles'] && data['roles'].indexOf('ADMIN')) > -1;
      this.author = (this.authenticated && data['roles'] && data['roles'].indexOf('AUTHOR')) > -1;
    });
  }

  authenticate() {
    this.principal = this.authService.principal();
    if (this.principal != false) {
      this.authenticated = true;
    } else this.authenticated = false;
  }


}


