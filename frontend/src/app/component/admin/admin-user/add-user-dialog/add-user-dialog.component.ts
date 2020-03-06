import {Component, Inject, OnInit} from '@angular/core';
import {User} from "../../../../model/user";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UserService} from "../../../../service/user.service";
import {UtilsService} from "../../../../service/utils.service";

@Component({
  selector: 'app-add-user-dialog',
  templateUrl: './add-user-dialog.component.html',
  styleUrls: ['./add-user-dialog.component.css']
})
export class AddUserDialogComponent implements OnInit {
  user: User = new User();
  returnUser: User = new User();

  constructor(private userService: UserService, public dialogRef: MatDialogRef<AddUserDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private utilsService: UtilsService) {
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
            this.utilsService.alert("user created");
          } else {
            this.utilsService.alert("login or email exist");
          }
        }
      );
    } else this.utilsService.alert("fill all fields");
  }

}
