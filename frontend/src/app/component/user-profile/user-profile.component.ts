import {Component, OnInit} from '@angular/core';
import {User} from "../../model/user";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user: User;
  editForm: FormGroup;
  passDiv = false;
  checkDiv = false;
  oldPass = null;
  newPass = null;
  confPass = null;
  invalid = false;
  check: Boolean = false;
  button = true;

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }

  ngOnInit() {
    let email = window.localStorage.getItem("email");
    if (!email) {
      alert("Invalid action.");
      this.router.navigate(['/']);
      return;
    }
    this.editForm = this.formBuilder.group({
      id: ['', Validators.required],
      login: ['', Validators.required],
      firstName: ['', Validators.required],
      email: ['', Validators.required],
      lastName: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.userService.getByEmail(email)
      .subscribe(data => {
        this.editForm.setValue(data);
      });
  }

  comparison() {
    if (this.newPass != this.confPass) {
      window.alert('Passwords dont match');
      (<HTMLButtonElement>document.getElementById('save')).disabled = true;
    }
  }

  changePass() {
    this.checkDiv = true;
    this.button = false;
  }

  checkPass() {
    this.userService.checkPass(localStorage.getItem('id'), this.oldPass).subscribe(data => {
      this.check = data;
      if (this.check != true) {
        window.alert("Wrong password");
      } else {
        this.passDiv = true;
        this.checkDiv = false;
      }
    })
  }

  savePass() {
    let email = window.localStorage.getItem("email");
    this.userService.savePass(localStorage.getItem('id'), this.confPass).subscribe(data => {
      this.userService.getByEmail(email)
        .subscribe(data => {
          this.editForm.setValue(data);
        });
    });
    window.alert('Password is updated!');
    this.passDiv = false;
    this.checkDiv = false;
    this.oldPass = null;
    this.newPass = null;
    this.confPass = null;
    this.invalid = false;
    this.check = false;
    this.button = true;
  }


  onSubmit() {
    let id = window.localStorage.getItem("id");
    this.userService.update(id, this.editForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data != null) {
            alert('User updated successfully.');
            this.router.navigate(['profile']);
          } else {
            alert('User does not update');
          }
        },
        error => {
          alert(error);
        });
  }

}
