import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserListComponent} from "./component/admin/admin-user/user-list.component";
import {UserFormComponent} from "./component/admin/user-form/user-form.component";
import {CategoryListComponent} from "./component/category-list/category-list.component";
import {LoginComponent} from "./component/auth/login/login.component";
import {UserProfileComponent} from "./component/user-profile/user-profile.component";
import {RegisterComponent} from "./component/auth/register/register.component";
import {RecipeListComponent} from "./component/recipe-list/recipe-list.component";
import {RecipeAddComponent} from "./component/recipe-add/recipe-add.component";
import {RecipeViewComponent} from "./component/recipe-view/recipe-view.component";
import {RecipeAuthorComponent} from "./component/recipe-author/recipe-author.component";
import {RecipeEditComponent} from "./component/recipe-edit/recipe-edit.component";
import {SearchComponent} from "./component/search/search.component";
import {AdminPanelComponent} from "./component/admin/admin-panel/admin-panel.component";
import {AddCategoryComponent} from "./component/admin/admin-category/add-category.component";
import {AddIngredientComponent} from "./component/admin/admin-ingredient/add-ingredient.component";
import {FavoriteComponent} from "./component/favorite/favorite.component";


const routes: Routes = [
  {path: 'users', component: UserListComponent},
  {path: 'adduser', component: UserFormComponent},
  {path: 'category', component: CategoryListComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: UserProfileComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'recipe-list', component: RecipeListComponent},
  {path: 'addRecipe', component: RecipeAddComponent},
  {path: 'recipe-view', component: RecipeViewComponent},
  {path: 'recipe-author', component: RecipeAuthorComponent},
  {path: 'recipe-edit', component: RecipeEditComponent},
  {path: 'search', component: SearchComponent},
  {path: 'admin', component: AdminPanelComponent},
  {path: 'addCategory', component: AddCategoryComponent},
  {path: 'addIngredient', component: AddIngredientComponent},
  {path: 'favorite', component: FavoriteComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
