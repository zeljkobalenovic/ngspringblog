import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { PostPayload } from './postPayload';
import { PostService } from '../post.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-addpost',
  templateUrl: './addpost.component.html',
  styleUrls: ['./addpost.component.css']
})
export class AddpostComponent implements OnInit {

  addPostForm : FormGroup;
  title = new FormControl('');
  content = new FormControl('');
  postPayload:PostPayload;  

  // reactive forma lakse sa builderom , ali moze i ovako 
  constructor( private postService:PostService , private router:Router) {
    this.addPostForm = new FormGroup ({
      title:this.title ,
      content:this.content
    })
    this.postPayload = {
      id:'',
      title:'',
      content:'',
      userName:''
    }
   }

  ngOnInit() {
  }

  addPost() {
    this.postPayload.title=this.addPostForm.get('title').value;
    this.postPayload.content=this.addPostForm.get('content').value;
    this.postService.addPost(this.postPayload).subscribe(data => {
        this.router.navigateByUrl('/home');
    },error => {
        console.log("Failure response");
    })
  }

}
