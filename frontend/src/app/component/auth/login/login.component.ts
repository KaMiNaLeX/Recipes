import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../../service/auth.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  email = '';
  password = '';

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService,
              public dialogRef: MatDialogRef<LoginComponent>) {
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
          window.location.reload();
        }
      }, (err) => {
        console.log(err);
      });
  }

  onNoClick() {
    this.dialogRef.close();
  }
}
