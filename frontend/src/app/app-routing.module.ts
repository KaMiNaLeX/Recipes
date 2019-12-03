import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {UserListComponent} from "./component/user-list/user-list.component";
import {UserFormComponent} from "./component/user-form/user-form.component";
import {CategoryListComponent} from "./component/category-list/category-list.component";
import {LoginComponent} from "./component/auth/login/login.component";
import {UserProfileComponent} from "./component/user-profile/user-profile.component";
import {RegisterComponent} from "./component/auth/register/register.component";


const routes: Routes = [
  {path: 'users', component: UserListComponent},
  {path: 'adduser', component: UserFormComponent},
  {path: 'category', component: CategoryListComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: UserProfileComponent},
  {path: 'registry', component: RegisterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
