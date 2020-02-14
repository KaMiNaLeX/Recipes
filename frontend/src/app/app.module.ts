import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {UserListComponent} from './component/admin/user-list/user-list.component';
import {UserFormComponent} from './component/admin/user-form/user-form.component';
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
import {AddCategoryComponent} from './component/admin/add-category/add-category.component';
import {AddIngredientComponent} from './component/admin/add-ingredient/add-ingredient.component';
import {FavoriteComponent} from './component/favorite/favorite.component';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";

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
    FormsModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
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
