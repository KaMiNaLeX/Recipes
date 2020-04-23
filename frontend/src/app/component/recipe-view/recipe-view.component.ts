import {Component, OnInit} from '@angular/core';
import {CreateRecipeDTO} from "../../model/createRecipe/create-recipe-dto";
import {RecipeService} from "../../service/recipe.service";
import {SharedService} from "../../service/shared.service";
import {FavoriteService} from "../../service/favorite.service";
import {Comment} from "../../model/comment";
import {CommentsService} from "../../service/comments.service";
import {UtilsService} from "../../service/utils.service";

@Component({
  selector: 'app-recipe-view',
  templateUrl: './recipe-view.component.html',
  styleUrls: ['./recipe-view.component.css']
})
export class RecipeViewComponent implements OnInit {
  createRecipeDTO: CreateRecipeDTO = new CreateRecipeDTO();
  ru: boolean;
  dataSource: any;
  dataSource2: any;
  displayedColumns: string[] = ['ingredient', 'amount', 'unit'];
  displayedColumns2: string[] = ['photo', 'description'];
  authenticated = false;
  isAuthor = false;
  isCreator = false;
  comments: Comment[] = [];
  comment: Comment = new Comment();
  editForm: boolean[] = [false, false, false, false, false, false, false, false, false, false];
  addForm = true;
  count: number;
  pageCommentCounter: number = 0;
  sizeCommentCounter: number = 10;

  constructor(private createRecipeService: RecipeService, private ss: SharedService, private favoriteService: FavoriteService,
              private commentService: CommentsService, private utilsService: UtilsService) {
    if (localStorage.getItem('token') != undefined) {
      this.authenticated = true;
    }
  }

  ngOnInit() {
    this.commentService.getCountAllComments(sessionStorage.getItem('recipe')).subscribe(data => {
      this.count = data;
    });
    this.commentService.findByRecipeId(sessionStorage.getItem('recipe'), 0, 10, 'creationDate').subscribe(data => {
      this.comments = data;
      for (let i = 0; i < this.comments.length; i++) {
        if (this.comments[i].creatorId == localStorage.getItem('id')) {
          this.isCreator = true;
        }
        if (this.comments[i].creatorId == this.createRecipeDTO.authorId) {
          this.isAuthor = true;
        }
      }
    });
    if (this.authenticated != false) {
      this.createRecipeService.getById2(sessionStorage.getItem('recipe'), localStorage.getItem('id')).subscribe(
        data => {
          this.createRecipeDTO = data;
          this.dataSource = this.createRecipeDTO.ingredientRecipeDTOList;
          this.dataSource2 = this.createRecipeDTO.cookingStepRecipeDTOList.reverse();
          console.log(this.createRecipeDTO.inFavorite);
        });
    } else {
      this.createRecipeService.getById(sessionStorage.getItem('recipe')).subscribe(
        data => {
          this.createRecipeDTO = data;
          this.dataSource = this.createRecipeDTO.ingredientRecipeDTOList;
          this.dataSource2 = this.createRecipeDTO.cookingStepRecipeDTOList.reverse();
        });
    }

    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => this.ru = item);
  }

  addToFavorite(id: string) {
    this.favoriteService.addToFavorite(id, this.authenticated);
    if (this.authenticated != false) {
      this.createRecipeDTO.inFavorite = true;
    }
  }

  deleteFromFavorite(recipeId: string) {
    this.favoriteService.deleteByUserAndRecipeId(localStorage.getItem('id'), recipeId).subscribe(data => {
      this.createRecipeDTO.inFavorite = false;
    });
  }

  addComment() {
    if (this.authenticated != false) {
      this.comment.creatorId = localStorage.getItem('id');
      this.comment.recipeId = sessionStorage.getItem('recipe');
      this.commentService.create(this.comment).subscribe(data => {
        this.comment.text = ' ';
        this.commentService.findByRecipeId(sessionStorage.getItem('recipe'), this.pageCommentCounter, 10, 'creationDate').subscribe(data => {
          this.comments = data;
          for (let i = 0; i < this.comments.length; i++) {
            if (this.comments[i].creatorId == localStorage.getItem('id')) {
              this.isCreator = true;
            }
            if (this.comments[i].creatorId == this.createRecipeDTO.authorId) {
              this.isAuthor = true;
            }
          }
        });
        this.commentService.getCountAllComments(sessionStorage.getItem('recipe')).subscribe(data => {
          this.count = data;
        });
      });
    } else this.utilsService.alert("you need to authenticated")
  }

  deleteComment(id: string) {
    this.commentService.delete(id).subscribe(data => {
      this.commentService.findByRecipeId(sessionStorage.getItem('recipe'), this.pageCommentCounter, 10, 'creationDate').subscribe(data => {
        this.comments = data;
        for (let i = 0; i < this.comments.length; i++) {
          if (this.comments[i].creatorId == localStorage.getItem('id')) {
            this.isCreator = true;
          }
          if (this.comments[i].creatorId == this.createRecipeDTO.authorId) {
            this.isAuthor = true;
          }
        }
      });
      this.commentService.getCountAllComments(sessionStorage.getItem('recipe')).subscribe(data => {
        this.count = data;
      });
    });
  }

  editComment(id: string, text: string) {
    this.comment.creatorId = localStorage.getItem('id');
    this.comment.recipeId = sessionStorage.getItem('recipe');
    this.comment.text = text;
    this.commentService.update(id, this.comment).subscribe(data => {
      this.comment.text = ' ';
      this.commentService.findByRecipeId(sessionStorage.getItem('recipe'), this.pageCommentCounter, 10, 'creationDate').subscribe(data => {
        this.comments = data;
        for (let i = 0; i < this.comments.length; i++) {
          if (this.comments[i].creatorId == localStorage.getItem('id')) {
            this.isCreator = true;
          }
          if (this.comments[i].creatorId == this.createRecipeDTO.authorId) {
            this.isAuthor = true;
          }
        }
      });
    })
  }

  loadComments() {
    this.pageCommentCounter += 1;
    this.sizeCommentCounter += 10;
    this.commentService.findByRecipeId(sessionStorage.getItem('recipe'), this.pageCommentCounter,
      10, 'creationDate').subscribe(data => {
      this.comments = data;
      for (let i = 0; i < this.comments.length; i++) {
        if (this.comments[i].creatorId == localStorage.getItem('id')) {
          this.isCreator = true;
        }
        if (this.comments[i].creatorId == this.createRecipeDTO.authorId) {
          this.isAuthor = true;
        }
      }
    });
  }

  loadPrevComments() {
    this.pageCommentCounter -= 1;
    this.sizeCommentCounter -= 10;
    this.commentService.findByRecipeId(sessionStorage.getItem('recipe'), this.pageCommentCounter,
      10, 'creationDate').subscribe(data => {
      this.comments = data;
      for (let i = 0; i < this.comments.length; i++) {
        if (this.comments[i].creatorId == localStorage.getItem('id')) {
          this.isCreator = true;
        }
        if (this.comments[i].creatorId == this.createRecipeDTO.authorId) {
          this.isAuthor = true;
        }
      }
    });
  }
}
