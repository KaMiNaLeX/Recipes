import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: any = {};

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {
  }

  ngOnInit() {
    sessionStorage.setItem('token', '');
  }

  login() {
    let url = '/client/login';
    let result = this.http.post<Observable<boolean>>(url, {
      userName: this.user.username,
      password: this.user.password
    }).subscribe(isValid => {
      if (isValid) {
        sessionStorage.setItem(
          'token',
          btoa(this.user.username + ':' + this.user.password)
        );
        this.router.navigate(['/']);
      } else {
        alert("Authentication failed.")
      }
    });
  }

}
