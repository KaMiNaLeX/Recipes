import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserListComponent} from "./component/admin/user-list/user-list.component";
import {UserFormComponent} from "./component/admin/user-form/user-form.component";
import {CategoryListComponent} from "./component/category-list/category-list.component";
import {LoginComponent} from "./component/auth/login/login.component";
import {UserProfileComponent} from "./component/user-profile/user-profile.component";
import {RegisterComponent} from "./component/auth/register/register.component";
import {RecipeListComponent} from "./component/recipe-list/recipe-list.component";
import {RecipeAddComponent} from "./component/recipe-add/recipe-add.component";
import {RecipeViewComponent} from "./component/recipe-view/recipe-view.component";


const routes: Routes = [
  {path: 'users', component: UserListComponent},
  {path: 'adduser', component: UserFormComponent},
  {path: 'category', component: CategoryListComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: UserProfileComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'recipe-list', component: RecipeListComponent},
  {path: 'addRecipe', component: RecipeAddComponent},
  {path: 'recipe-view', component: RecipeViewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
