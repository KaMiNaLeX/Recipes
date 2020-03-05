import {Component, Inject, OnInit} from '@angular/core';
import {User} from "../../../../model/user";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UserService} from "../../../../service/user.service";

@Component({
  selector: 'app-add-user-dialog',
  templateUrl: './add-user-dialog.component.html',
  styleUrls: ['./add-user-dialog.component.css']
})
export class AddUserDialogComponent implements OnInit {
  user: User = new User();
  returnUser: User = new User();

  constructor(private userService: UserService, public dialogRef: MatDialogRef<AddUserDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  addUser() {
    if (this.user.firstName != null && this.user.lastName != null && this.user.login != null &&
      this.user.email != null && this.user.password != null) {
      this.userService.create(this.user).subscribe(data => {
          this.returnUser = data;
          if (this.returnUser != null) {
            window.location.reload();
            window.alert("User is created!");
          } else {
            window.alert("User with this login or email already exists!");
          }
        }
      );
    } else window.alert("Please, fill in all fields!");
  }

}
