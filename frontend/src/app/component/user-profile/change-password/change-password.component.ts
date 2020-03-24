import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../service/user.service";
import {UtilsService} from "../../../service/utils.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  oldPass = null;
  newPass = null;
  confPass = null;
  invalid = false;
  check: Boolean = false;

  constructor(private userService: UserService,
              private utilsService: UtilsService, public dialogRef: MatDialogRef<ChangePasswordComponent>, private dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  comparison() {
    if (this.newPass != this.confPass) {
      this.utilsService.alert('passwords don`t match');
      (<HTMLInputElement>document.getElementById('conf')).value = null;
      this.confPass = null;
    }
  }

  savePass() {
    let email = window.localStorage.getItem("email");
    this.userService.savePass(localStorage.getItem('id'), this.confPass).subscribe(data => {
      this.userService.getByEmail(email)
        .subscribe(data => {
        });
    });
    this.dialogRef.close();
    this.utilsService.alert("password is updated");
  }

  checkPass() {
    this.userService.checkPass(localStorage.getItem('id'), this.oldPass).subscribe(data => {
      this.check = data;
      if (this.check != true) {
        this.utilsService.alert("wrong password");
        (<HTMLInputElement>document.getElementById('old')).value = null;
        this.oldPass = null;
      } else {
      }
    })
  }

  onNoClick() {
    this.dialogRef.close();
  }
}
