import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './page/login/login.component';
import { RegisterComponent } from './page/register/register.component';
import { AuthenticateComponent } from './page/authenticate/authenticate.component';
import { HomeComponent } from './page/home/home.component';
import { MaterialModule } from 'src/material.module';
import { HeaderComponent } from './component/header/header.component';
import { ShareComponent } from './component/share/share.component';
import { PostsComponent } from './component/posts/posts.component';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fab } from '@fortawesome/free-brands-svg-icons';
import { FeedComponent } from './component/feed/feed.component';
import { MessengerComponent } from './page/messenger/messenger.component';
import { ConversationComponent } from './component/conversation/conversation.component';
import { MessageComponent } from './component/message/message.component';
import { CreatePasswordComponent } from './component/create-password/create-password.component';
import { CommentComponent } from './component/comment/comment.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    AuthenticateComponent,
    HomeComponent,
    HeaderComponent,
    ShareComponent,
    PostsComponent,
    FeedComponent,
    MessengerComponent,
    ConversationComponent,
    MessageComponent,
    CreatePasswordComponent,
    CommentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,  
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    FontAwesomeModule,
   FormsModule
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor(library: FaIconLibrary) {
    // Add specific icons or entire icon packs to the library
    library.addIconPacks(fas, far, fab);
  }
}
