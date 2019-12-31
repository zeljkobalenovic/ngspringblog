import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../post.service';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { PostPayload } from '../addpost/postPayload';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  permaLink: Number;
  post: PostPayload;

  constructor(private router:ActivatedRoute , private posrService:PostService) { }

  ngOnInit() {
    this.router.params.subscribe(params=>{
      this.permaLink=params['id'];
    });

    this.posrService.getPost(this.permaLink).subscribe((data:PostPayload)=> {
      this.post=data;
    } , (error:any)=> {
      console.log("Failure response")
    });
  }

}
