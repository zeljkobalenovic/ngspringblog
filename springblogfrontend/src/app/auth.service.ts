import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterPayload } from './auth/register-payload';
import { Observable } from 'rxjs';
import { LoginPayload } from './auth/login-payload';
import { JwtAuthResponse } from './auth/jwt-auth-response';




@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = 'http://localhost:8080/api/auth/'; 

  constructor(private httpClient:HttpClient) { }

  register(registerPayload:RegisterPayload) : Observable<any> {
    return this.httpClient.post(this.url + "signup" , registerPayload);
  }

  login(loginPayload:LoginPayload) : Observable<any> {
    return this.httpClient.post<JwtAuthResponse>(this.url + "login" , loginPayload);
  }

}
