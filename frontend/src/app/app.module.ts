import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {UserListComponent} from './component/admin/admin-user/user-list.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {CategoryListComponent} from './component/category-list/category-list.component';
import {LoginComponent} from "./component/auth/login/login.component";
import {TokenInterceptor} from "./interceptors/token.interceptor";
import {UserProfileComponent} from './component/user-profile/user-profile.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RegisterComponent} from './component/auth/register/register.component';
import {RecipeListComponent} from './component/recipe-list/recipe-list.component';
import {RecipeAddComponent} from './component/recipe-add/recipe-add.component';
import {RecipeViewComponent} from './component/recipe-view/recipe-view.component';
import {RecipeAuthorComponent} from './component/recipe-author/recipe-author.component';
import {RecipeEditComponent} from './component/recipe-edit/recipe-edit.component';
import {SearchComponent} from './component/search/search.component';
import {AdminPanelComponent} from './component/admin/admin-panel/admin-panel.component';
import {AddCategoryComponent} from './component/admin/admin-category/add-category.component';
import {AddIngredientComponent} from './component/admin/admin-ingredient/add-ingredient.component';
import {FavoriteComponent} from './component/favorite/favorite.component';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {MatSliderModule} from "@angular/material/slider";
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatSortModule} from "@angular/material/sort";
import { BackButtonComponent } from './component/back-button/back-button.component';
import { AddCategoryDialogComponent } from './component/admin/admin-category/add-category-dialog/add-category-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import { EditCategoryDialogComponent } from './component/admin/admin-category/edit-category-dialog/edit-category-dialog.component';
import { DeleteCategoryDialogComponent } from './component/admin/admin-category/delete-category-dialog/delete-category-dialog.component';
import { AddIngredientDialogComponent } from './component/admin/admin-ingredient/add-ingredient-dialog/add-ingredient-dialog.component';
import { EditIngredientDialogComponent } from './component/admin/admin-ingredient/edit-ingredient-dialog/edit-ingredient-dialog.component';
import { DeleteIngredientDialogComponent } from './component/admin/admin-ingredient/delete-ingredient-dialog/delete-ingredient-dialog.component';
import {MatSelectModule} from "@angular/material/select";
import { AddUserDialogComponent } from './component/admin/admin-user/add-user-dialog/add-user-dialog.component';
import { EditUserDialogComponent } from './component/admin/admin-user/edit-user-dialog/edit-user-dialog.component';
import { DeleteUserDialogComponent } from './component/admin/admin-user/delete-user-dialog/delete-user-dialog.component';
import { AlertComponent } from './component/alert/alert.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import { ChangePasswordComponent } from './component/user-profile/change-password/change-password.component';
import {MatTabsModule} from "@angular/material/tabs";
import {MatChipsModule} from "@angular/material/chips";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatDividerModule} from "@angular/material/divider";
import {MatRadioModule} from "@angular/material/radio";

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
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
    FavoriteComponent,
    BackButtonComponent,
    AddCategoryDialogComponent,
    EditCategoryDialogComponent,
    DeleteCategoryDialogComponent,
    AddIngredientDialogComponent,
    EditIngredientDialogComponent,
    DeleteIngredientDialogComponent,
    AddUserDialogComponent,
    EditUserDialogComponent,
    DeleteUserDialogComponent,
    AlertComponent,
    ChangePasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
    , MatSliderModule, MatButtonModule, MatToolbarModule, MatMenuModule, MatIconModule, MatCardModule, MatTableModule, MatPaginatorModule, MatFormFieldModule, MatInputModule, MatSortModule, MatDialogModule, MatSelectModule, MatCheckboxModule, MatTabsModule, MatChipsModule, MatAutocompleteModule, MatDividerModule, MatRadioModule
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

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
