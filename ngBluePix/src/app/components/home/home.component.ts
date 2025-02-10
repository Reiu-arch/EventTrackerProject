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

  editPost: Post | null = null;

  showEditngForm: boolean = false;

  setEditPost(post: Post) {
    this.editPost = { ...post };
    this.showEditngForm = true;
  }

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

  updatePost(post: Post) {
    console.log(post);
    this.postService.update(post).subscribe( {
      next: (updatingTodo) => {
        this.reload();
        this.editPost = null;
        this.showEditngForm = false;
      },
      error: (fail) => {
        console.log('homeComponent.updatePost: failed to update.');
        console.log(fail);
      }
    }
  );
  }

  deletePost(postId: number){
    console.log(postId);
    this.postService.destroy(postId).subscribe({
      next: () => {
        this.reload();
        this.editPost = null;
        this.showEditngForm = false;
      },
      error: (fail) => {
        console.log('homeComponent.deletePost: failed to delete the Post.')
        console.log(fail);
      }
    })
  }
}
