import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UserService} from "../../../../service/user.service";
import {User} from "../../../../model/user";

@Component({
  selector: 'app-edit-user-dialog',
  templateUrl: './edit-user-dialog.component.html',
  styleUrls: ['./edit-user-dialog.component.css']
})
export class EditUserDialogComponent implements OnInit {
  user: User = new User();
  returnUser: User = new User();

  constructor(private userService: UserService, public dialogRef: MatDialogRef<EditUserDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: User) {
  }

  ngOnInit(): void {
    this.userService.get(this.data.id).subscribe(data => {
      this.user = data;
    });
  }

  editUser() {
    if (this.user.firstName != null && this.user.lastName != null && this.user.login != null &&
      this.user.email != null && this.user.password != null) {
      this.userService.update(this.user.id, this.user).subscribe(data => {
          this.returnUser = data;
          if (this.returnUser != null) {
            window.location.reload();
            window.alert("User is updated!");
          } else {
            window.alert("User with this login or email already exists!");
          }
        }
      );
    } else window.alert("Please, fill in all fields!");
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
