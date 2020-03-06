import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../../service/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  email = '';
  password = '';

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      'email': [null, [Validators.required, Validators.email]],
      'password': [null, Validators.required]
    });
  }

  onFormSubmit(form: NgForm) {
    this.authService.login(form)
      .subscribe(res => {
        if (res.token && res.username && res.id) {
          localStorage.setItem('token', res.token);
          localStorage.setItem('email', res.username);
          localStorage.setItem('id', res.id);
          this.router.navigateByUrl('/category').then(() => {
            window.location.reload();
          });
        }
      }, (err) => {
        console.log(err);
      });
  }

  register() {
    this.router.navigate(['register']);
  }

}
