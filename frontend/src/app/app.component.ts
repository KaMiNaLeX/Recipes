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

  login() {
    this.router.navigate(['login']);
  }

  recipes() {
    this.router.navigate(['recipe-author']);
  }

  search() {
    this.router.navigate(['search']);
  }

  categories() {
    this.router.navigate(['category']);
  }

  register() {
    this.router.navigate(['register']);
  }

  profile() {
    this.router.navigate(['profile']);
  }

  users() {
    this.router.navigate(['users']);
  }

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService,
              private http: HttpClient) {
    if (localStorage.getItem('token') != undefined) {
      this.authenticated = true;
      this.role();
    }
  }

  role() {
    this.authService.role().subscribe(data => {
      this.admin = (data['roles'] && data['roles'].indexOf('ADMIN')) > -1;
      this.author = (data['roles'] && data['roles'].indexOf('AUTHOR')) > -1;
    });
  }
}


