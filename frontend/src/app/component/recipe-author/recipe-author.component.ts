import {Component, OnInit} from '@angular/core';
import {RecipeService} from "../../service/recipe.service";
import {CreateRecipeDTO} from "../../model/createRecipe/create-recipe-dto";
import {Router} from "@angular/router";
import {SharedService} from "../../service/shared.service";
import {UtilsService} from "../../service/utils.service";
import {PageEvent} from "@angular/material/paginator";
import {Vote} from "../../model/vote";
import {VotesService} from "../../service/votes.service";

@Component({
  selector: 'app-recipe-author',
  templateUrl: './recipe-author.component.html',
  styleUrls: ['./recipe-author.component.css']
})
export class RecipeAuthorComponent implements OnInit {
  recipes: CreateRecipeDTO[] = [];
  recipe: CreateRecipeDTO = new CreateRecipeDTO();
  ru: boolean;
  // MatPaginator Inputs
  length: number;
  pageSize = 8;
  pageIndex = 0;
  pageSizeOptions: number[] = [8, 32, 64];
  // MatPaginator Output
  pageEvent: PageEvent;
  //votes
  vote: Vote = new Vote();

  constructor(private router: Router, private recipeService: RecipeService, private ss: SharedService,
              private utilsService: UtilsService, private votesService: VotesService) {
  }

  ngOnInit() {
    this.recipeService.getCountAllOwnRecipes(localStorage.getItem('id')).subscribe(data => {
      this.length = data;
    });
    this.recipeService.getByAuthorId(localStorage.getItem('id'), 0, this.pageSize, "name").subscribe(data => this.recipes = data);
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => this.ru = item);
  }

  view(id: string) {
    sessionStorage.setItem('recipe', id);
    this.router.navigate(['recipe-view']);
  }

  edit(id: string) {
    sessionStorage.setItem('recipe', id);
    this.router.navigate(['recipe-edit']);
  }

  addRecipe() {
    this.router.navigate(['addRecipe']);
  }

  delete(id: string) {
    this.recipeService.delete(id)
      .subscribe(
        data => {
          console.log(data);
          this.recipeService.getByAuthorId(localStorage.getItem('id'), 0, this.pageSize, "name").subscribe(data => {
            this.recipes = data;
            this.recipeService.getCountAllOwnRecipes(localStorage.getItem('id')).subscribe(data => {
              this.length = data;
            });
          })
        },
        error => console.log(error));
    this.utilsService.alert("recipe deleted");
  }

  getServerData(event?: PageEvent) {
    this.recipeService.getByAuthorId(localStorage.getItem('id'), event.pageIndex, event.pageSize, "name").subscribe(
      response => {
        this.recipes = response;
        this.pageIndex = event.pageIndex;
        this.pageSize = event.pageSize;
      }
    );
    return event;
  }

  like(id: string) {
    this.vote.recipeId = id;
    this.vote.userId = localStorage.getItem('id');
    this.vote.positiveVote = true;
    this.vote.negativeVote = false;
    this.votesService.createVote(this.vote).subscribe(data => {
      if (data != null) {
        this.recipeService.getByAuthorId(localStorage.getItem('id'), this.pageIndex, this.pageSize, "name").subscribe(
          response => {
            this.recipes = response;
          }
        );
      } else this.utilsService.alert("evaluated")
    })
  }

  dislike(id: string) {
    this.vote.recipeId = id;
    this.vote.userId = localStorage.getItem('id');
    this.vote.negativeVote = true;
    this.vote.positiveVote = false;
    this.votesService.createVote(this.vote).subscribe(data => {
      if (data != null) {
        this.recipeService.getByAuthorId(localStorage.getItem('id'), this.pageIndex, this.pageSize, "name").subscribe(
          response => {
            this.recipes = response;
          }
        );
      } else this.utilsService.alert("evaluated")
    });
  }
}
