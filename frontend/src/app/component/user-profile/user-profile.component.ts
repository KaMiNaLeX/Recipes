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

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }

  ngOnInit() {
    let email = window.localStorage.getItem("email");
    if (!email) {
      alert("Invalid action.")
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
