import {FormControl, Validators} from '@angular/forms';
import {User} from "../model/user";
import {UserService} from "../service/user.service";

export class CustomValidator extends Validators {

  private static userService: UserService;
  private static user: User = new User();

  // create a static method for your validation
  static validateCharacters(control: FormControl) {

    if (control.value && control.value.length > 0) {
      this.userService.getByLogin(control.value).subscribe(data => {
        if (data.login.length != 0) {
          this.user = data;
        }
      });

      // if there are matches return an object, else return null.
      return this.user.login.length ? {invalid_characters: this.user.login} : null;
    } else {
      return null;
    }
  }
}
