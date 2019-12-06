import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {UserListComponent} from './component/user-list/user-list.component';
import {UserFormComponent} from './component/user-form/user-form.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {UserService} from "./service/user.service";
import {CategoryListComponent} from './component/category-list/category-list.component';
import {LoginComponent} from "./component/auth/login/login.component";
import {TokenInterceptor} from "./interceptors/token.interceptor";
import {CategoryService} from "./service/category.service";
import {UserProfileComponent} from './component/user-profile/user-profile.component';

import {
  MatInputModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatSortModule,
  MatTableModule,
  MatIconModule,
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule
} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { RegisterComponent } from './component/auth/register/register.component';
import {AuthService} from "./service/auth.service";
import { RecipeListComponent } from './component/recipe-list/recipe-list.component';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    UserFormComponent,
    LoginComponent,
    CategoryListComponent,
    UserProfileComponent,
    RegisterComponent,
    RecipeListComponent
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
    },
    {
      provide: UserService,
      useClass: UserService
    },
    {
      provide: CategoryService,
      useClass: CategoryService
    },
    {
      provide: AuthService,
      useClass: AuthService
    }

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
