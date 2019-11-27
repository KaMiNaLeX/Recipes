import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {UserListComponent} from "./user-list/user-list.component";
import {UserFormComponent} from "./user-form/user-form.component";
import {LoginComponent} from "./login/login.component";


const routes: Routes = [
  {path: 'users', component: UserListComponent},
  { path: 'login', component: LoginComponent },
  {path: 'adduser', component: UserFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
