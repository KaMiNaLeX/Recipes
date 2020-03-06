import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../../service/auth.service";
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/user";
import {UtilsService} from "../../../service/utils.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  firstName = '';
  lastName = '';
  login = '';
  email = '';
  password = '';
  isLoadingResults = false;
  existsUserEmail = null;
  existsUserLogin = null;
  user: User = new User();

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService,
              private userService: UserService, private utilsService: UtilsService) {
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      'firstName': new FormControl(null, Validators.required),
      'lastName': new FormControl(null, Validators.required),
      'login': new FormControl(null, [Validators.required]),
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'password': new FormControl(null, [Validators.required, Validators.minLength(5)]),
      'isAuthor': new FormControl()
    });
  }

  checkLogin() {
    let login = (<HTMLInputElement>document.getElementById('login')).value;
    if (login != null) {
      this.userService.getByLogin(login).subscribe(data => {
        this.user = data;
        if (this.user != null) {
          (<HTMLInputElement>document.getElementById('login')).value = null;
          this.utilsService.alert("login exist");
          this.registerForm.get('login').setValue(null);
        }
      })
    }
  }

  checkEmail() {
    let email = (<HTMLInputElement>document.getElementById('email')).value;
    if (email != null) {
      this.userService.getByEmail(email).subscribe(data => {
        this.user = data;
        if (this.user != null) {
          (<HTMLInputElement>document.getElementById('email')).value = null;
          this.utilsService.alert("email exist");
          this.registerForm.get('email').setValue(null);
        }
      })
    }
  }

  onFormSubmit(form: NgForm) {
    this.authService.register(form)
      .subscribe(res => {
        this.router.navigate(['login']);
      }, (err) => {
        console.log(err);
        alert(err.error);
      });
  }

  loginPage() {
    this.router.navigate(['login']);
  }

}
