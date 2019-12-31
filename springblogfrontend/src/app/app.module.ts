import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule , ReactiveFormsModule } from '@angular/forms'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterSuccessComponent } from './auth/register-success/register-success.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { HomeComponent } from './home/home.component';
import { AddpostComponent } from './addpost/addpost.component';
import { EditorModule } from '@tinymce/tinymce-angular';
import { HttpClientInterceptor } from './http-client-interceptor';



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
    RegisterSuccessComponent,
    HomeComponent,
    AddpostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgxWebstorageModule.forRoot(),
    /*
    RouterModule.forRoot([
      { path: 'register', component:RegisterComponent }, 
      { path: 'login', component:LoginComponent },
      { path: 'register-success', component:RegisterSuccessComponent },
      { path: 'home' , component:HomeComponent },
      { path: 'addpost' , component:AddpostComponent },              
    ]
    ),*/

    RouterModule.forRoot([
      {path: '', component: HomeComponent},
      {path: 'register', component: RegisterComponent},      
      {path: 'login', component: LoginComponent},
      {path: 'register-success', component: RegisterSuccessComponent},
      {path: 'home', component: HomeComponent},
      {path: 'addpost', component: AddpostComponent}
    ]),



    HttpClientModule,
    EditorModule
  ],
  providers: [{provide:HTTP_INTERCEPTORS, useClass:HttpClientInterceptor, multi:true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
