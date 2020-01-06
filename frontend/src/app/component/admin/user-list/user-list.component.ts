import {Component, OnInit} from '@angular/core';
import {User} from "../../../model/user";
import {UserService} from "../../../service/user.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];
  user: User = new User();
  showDeleteMessage = false;
  firstDiv = true;
  secondDiv = false;
  thirdDiv = false;

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
    });
  }

  toAdmin() {
    this.router.navigate(['admin']);
  }

  toView() {
    this.firstDiv = true;
    this.secondDiv = false;
    this.thirdDiv = false;
  }

  add() {
    this.firstDiv = false;
    this.secondDiv = true;
  }

  addUser() {
    if (this.user.firstName != null && this.user.lastName != null && this.user.login != null &&
      this.user.email != null && this.user.password != null) {
      this.userService.create(this.user).subscribe(data => {
          this.userService.findAll().subscribe(data => {
            this.users = data;
          })
        }
      );
      this.firstDiv = true;
      this.secondDiv = false;
      window.alert("User is created!");
    } else window.alert("Please, fill in all fields!");
  }

  deleteUser(id: string) {
    this.userService.delete(id)
      .subscribe(
        data => {
          console.log(data);
          this.showDeleteMessage = true;
          this.userService.findAll().subscribe(data => {
            this.users = data;
          })
        },
        error => console.log(error));
  }

  view(id: string) {
    window.alert(id);
  }

  edit(id: string) {
    this.firstDiv = false;
    this.thirdDiv = true;
    this.userService.get(id).subscribe(data => {
      this.user = data;
    });
  }

  editUser() {
    if (this.user.firstName != null && this.user.lastName != null && this.user.login != null &&
      this.user.email != null && this.user.password != null) {
      this.userService.update(this.user.id, this.user).subscribe(data => {
          this.user = data;
          this.userService.findAll().subscribe(data => {
            this.users = data;
          })
        }
      );
      this.firstDiv = true;
      this.thirdDiv = false;
      window.alert("User is updated!");
    } else window.alert("Please, fill in all fields!");
  }

}
