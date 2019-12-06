import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserListComponent} from "./component/user-list/user-list.component";
import {UserFormComponent} from "./component/user-form/user-form.component";
import {CategoryListComponent} from "./component/category-list/category-list.component";
import {LoginComponent} from "./component/auth/login/login.component";
import {UserProfileComponent} from "./component/user-profile/user-profile.component";
import {RegisterComponent} from "./component/auth/register/register.component";
import {RecipeListComponent} from "./component/recipe-list/recipe-list.component";


const routes: Routes = [
  {path: 'users', component: UserListComponent},
  {path: 'adduser', component: UserFormComponent},
  {path: 'category', component: CategoryListComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: UserProfileComponent},
  {path: 'registry', component: RegisterComponent},
  {path: 'recipe-list', component: RecipeListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
