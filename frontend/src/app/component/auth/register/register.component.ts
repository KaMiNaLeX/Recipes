import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../../service/auth.service";
import {UserService} from "../../../service/user.service";

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

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService,
              private userService: UserService) {
  }

  //todo: need to fix
  ngOnInit() {
    this.registerForm = new FormGroup({
      'firstName': new FormControl(null, Validators.required),
      'lastName': new FormControl(null, Validators.required),
      'login': new FormControl(null, [Validators.required]),
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'password': new FormControl(null, [Validators.required, Validators.minLength(5)]),
      'isAuthor': new FormControl()
    });
  }

  userNameValidator(control: FormGroup): { [s: string]: boolean } {
    if (control.value != undefined) {
      this.existsUserEmail = this.userService.getByEmail(control.value);
      this.existsUserLogin = this.userService.getByLogin(control.value);
    }
    if (this.existsUserEmail != null) {
      return {"email": true};
    }
    if (this.existsUserLogin != null) {
      return {"login": true};
    }
    return null;
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
