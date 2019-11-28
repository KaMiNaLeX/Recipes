import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {UserListComponent} from "./user-list/user-list.component";
import {UserFormComponent} from "./user-form/user-form.component";
import {LoginComponent} from "./login/login.component";
import {CategoryListComponent} from "./category-list/category-list.component";


const routes: Routes = [
  {path: 'users', component: UserListComponent},
  {path: 'login', component: LoginComponent},
  {path: 'adduser', component: UserFormComponent},
  {path: 'category', component: CategoryListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
