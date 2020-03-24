import {Component} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "./service/auth.service";
import {HttpClient} from "@angular/common/http";
import {TranslateService} from "@ngx-translate/core";
import {SharedService} from "./service/shared.service";
import {UtilsService} from "./service/utils.service";
import {MatDialog} from "@angular/material/dialog";
import {LoginComponent} from "./component/auth/login/login.component";
import {RegisterComponent} from "./component/auth/register/register.component";
import {UserProfileComponent} from "./component/user-profile/user-profile.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Recipes';
  authenticated = false;
  admin = false;
  author = false;
  username = "unauthorized";

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService,
              private http: HttpClient, private translate: TranslateService, private ss: SharedService,
              private utilsService: UtilsService, private dialog: MatDialog) {
    if (localStorage.getItem('lang') !== null) {
      translate.setDefaultLang(localStorage.getItem('lang'));
    } else {
      translate.setDefaultLang('en');
      localStorage.setItem('lang', 'en');
    }
    if (localStorage.getItem('token') != undefined) {
      this.authenticated = true;
      this.role();
      this.username = localStorage.getItem('email');
    }
  }

  useLanguage(language: string) {
    this.translate.use(language);
    localStorage.setItem('lang', language);
    if (language == 'ru') {
      this.ss.ru();
    } else if (language == 'en') {
      this.ss.en();
    }
  }

  logout() {
    this.authService.logout();
    this.authenticated = false;
    this.admin = false;
    this.author = false;
    this.router.navigate(['category']).then(data =>
      window.location.reload()
    )
  }

  addRecipe() {
    if (this.author != false || this.admin != false) {
      this.router.navigate(['addRecipe']);
    } else {
      this.utilsService.alert("author or admin");
    }
  }

  login() {
    this.dialog.open(LoginComponent, {
      maxWidth: '30%',
      maxHeight: '50%',
      data: {}
    });
  }

  favorite() {
    this.router.navigate(['favorite']);
  }

  recipes() {
    this.router.navigate(['recipe-author']);
  }

  search() {
    this.router.navigate(['search']);
  }

  categories() {
    this.router.navigate(['category']);
  }

  register() {
    this.dialog.open(RegisterComponent, {
      maxWidth: '30%',
      maxHeight: '50%',
      data: {}
    });
  }

  profile() {
    this.dialog.open(UserProfileComponent, {
      maxWidth: '30%',
      maxHeight: '50%',
      data: {}
    });
  }

  users() {
    this.router.navigate(['users']);
  }

  role() {
    this.authService.role().subscribe(data => {
      this.admin = (data['roles'] && data['roles'].indexOf('ADMIN')) > -1;
      this.author = (data['roles'] && data['roles'].indexOf('AUTHOR')) > -1;
      localStorage.setItem('adminRole', this.admin.toString());
      localStorage.setItem('authorRole', this.author.toString());
    });
  }

  adminPanel() {
    this.router.navigate(['admin']);
  }
}


