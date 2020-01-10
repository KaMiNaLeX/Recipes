import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {UserListComponent} from './component/admin/user-list/user-list.component';
import {UserFormComponent} from './component/admin/user-form/user-form.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {CategoryListComponent} from './component/category-list/category-list.component';
import {LoginComponent} from "./component/auth/login/login.component";
import {TokenInterceptor} from "./interceptors/token.interceptor";
import {UserProfileComponent} from './component/user-profile/user-profile.component';

import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatSortModule,
  MatTableModule
} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RegisterComponent} from './component/auth/register/register.component';
import {RecipeListComponent} from './component/recipe-list/recipe-list.component';
import {RecipeAddComponent} from './component/recipe-add/recipe-add.component';
import { RecipeViewComponent } from './component/recipe-view/recipe-view.component';
import { RecipeAuthorComponent } from './component/recipe-author/recipe-author.component';
import { RecipeEditComponent } from './component/recipe-edit/recipe-edit.component';
import { SearchComponent } from './component/search/search.component';
import { AdminPanelComponent } from './component/admin/admin-panel/admin-panel.component';
import { AddCategoryComponent } from './component/admin/add-category/add-category.component';
import { AddIngredientComponent } from './component/admin/add-ingredient/add-ingredient.component';
import { FavoriteComponent } from './component/favorite/favorite.component';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    UserFormComponent,
    LoginComponent,
    CategoryListComponent,
    UserProfileComponent,
    RegisterComponent,
    RecipeListComponent,
    RecipeAddComponent,
    RecipeViewComponent,
    RecipeAuthorComponent,
    RecipeEditComponent,
    SearchComponent,
    AdminPanelComponent,
    AddCategoryComponent,
    AddIngredientComponent,
    FavoriteComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    FormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
