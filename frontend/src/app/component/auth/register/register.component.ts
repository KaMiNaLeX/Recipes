import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../../service/auth.service";
import {UserService} from "../../../service/user.service";
import {CustomValidator} from "../../../validators/custom-validator";

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
    this.registerForm = this.formBuilder.group({
      'firstName': new FormControl(null, Validators.required),
      'lastName': new FormControl(null, Validators.required),
      'login': new FormControl(null, [Validators.required,CustomValidator.validateCharacters]),
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'password': new FormControl(null, [Validators.required, Validators.minLength(5)]),
      'isAuthor': new FormControl()
    });
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
