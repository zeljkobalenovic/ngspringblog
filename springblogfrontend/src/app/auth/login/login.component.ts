import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { LoginPayload } from '../login-payload';
import { AuthService } from 'src/app/auth.service';
import { Router } from '@angular/router';
import { pipe } from 'rxjs';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm:FormGroup;
  loginPayload:LoginPayload;

  constructor(private formBuilder:FormBuilder , private authService:AuthService , private router:Router , private localStorageService:LocalStorageService ) {
    this.loginForm=this.formBuilder.group(
      {
        username:'',
        password:''
      }
    );
    this.loginPayload={
        username:'',
        password:''
    }
   }

  ngOnInit() {
  }

  onSubmit(){
    this.loginPayload.username=this.loginForm.get('username').value;
    this.loginPayload.password=this.loginForm.get('password').value;
    this.authService.login(this.loginPayload).subscribe( data=> {
          this.localStorageService.store('authenticationToken', data.authenticationToken);
          this.localStorageService.store('username', data.username);                 
          console.log("Login successful");
          this.router.navigateByUrl('/home');
      }, error => {
          console.log("Login failed")
      })   

  }



}
