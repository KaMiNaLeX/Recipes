import {Component, OnInit} from '@angular/core';
import {User} from "../../model/user";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {first} from "rxjs/operators";
import {UtilsService} from "../../service/utils.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {ChangePasswordComponent} from "./change-password/change-password.component";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user: User;
  editForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService,
              private utilsService: UtilsService, public dialogRef: MatDialogRef<UserProfileComponent>, private dialog: MatDialog) {
  }

  ngOnInit() {
    let email = window.localStorage.getItem("email");
    if (!email) {
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

  changePass() {
    this.dialog.open(ChangePasswordComponent, {
      maxWidth: '30%',
      maxHeight: '60%',
      data: {}
    });
  }

  onSubmit() {
    this.dialogRef.close();
    let id = window.localStorage.getItem("id");
    this.userService.update(id, this.editForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data != null) {
            this.utilsService.alert("user updated successfully");
            this.router.navigate(['category']);
          } else {
          }
        },
        error => {
          alert(error);
        });
  }

  onNoClick() {
    this.dialogRef.close();
  }
}
