import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Post } from '../../models/posts';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-home',
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{

  constructor(
    private postService: PostService,
  ){

  }

  ngOnInit() {
    this.reload();
  }

  posts: Post[] = [];

  newPost: Post = new Post();

  showAddingPost: boolean = false;


  reload(){
    this.postService.index().subscribe( {
      next: (postList) => {
        this.posts = postList;
      },
      error: (fail) => {
        console.log('TodoListComponent.reload: failed to load todos.')}
    } );
  }

  addingPost(newPosting: Post){
    this.postService.create(newPosting).subscribe( {
      next: (createdPost) => {
        this.newPost = createdPost;
        this.showAddingPost = false;
        this.reload();
        this.newPost = new Post();
      },
      error: (fail) => {
        console.log('homeComponent.addingPost: failed to add a new Post.');
        console.log(fail);
      }
    }
  );
  }
}
