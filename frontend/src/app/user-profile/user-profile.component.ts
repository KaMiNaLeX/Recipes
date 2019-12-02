import {Component, OnInit} from '@angular/core';
import {User} from "../model/user";
import {UserService} from "../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  id: string;
  user: User;

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService) {
  }

  ngOnInit() {
    this.user = new User();
    this.id = this.route.snapshot.params['id'];

    this.userService.get(this.id).subscribe(data => {
      console.log(data)
     // this.user = data;
    }, error => console.log(error));
  }

  update() {
    this.userService.update(this.id, this.user)
      .subscribe(data => console.log(data), error => console.log(error));
    this.user = new User();

  }

  onSubmit() {
    this.update();
  }


}
