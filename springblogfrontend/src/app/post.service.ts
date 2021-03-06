import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PostPayload } from './addpost/postPayload';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private url = 'http://localhost:8080/api/post/';

  constructor(private httpClient:HttpClient) { }

  addPost(postPayload:PostPayload) : Observable<any> {
    return this.httpClient.post(this.url + 'addpost' , postPayload);
  }

  getAllPost() : Observable<Array<PostPayload>> {
    return this.httpClient.get<Array<PostPayload>>(this.url + 'getposts')
  }

  getPost(permaLink: Number) : Observable<PostPayload>  {
    return this.httpClient.get<PostPayload>(this.url + 'getpost/' + permaLink)
  }
   
}
