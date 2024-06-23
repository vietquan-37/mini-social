import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './page/login/login.component';
import { RegisterComponent } from './page/register/register.component';
import { AuthenticateComponent } from './page/authenticate/authenticate.component';
import { HomeComponent } from './page/home/home.component';
import { MessengerComponent } from './page/messenger/messenger.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'authenticate', component: AuthenticateComponent },
  { path: '', component: HomeComponent },
  { path: 'message', component: MessengerComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
