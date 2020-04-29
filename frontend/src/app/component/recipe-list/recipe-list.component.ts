import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RecipeService} from "../../service/recipe.service";
import {Recipe} from "../../model/recipe";
import {FavoriteService} from "../../service/favorite.service";
import {Favorite} from "../../model/favorite";
import {SharedService} from "../../service/shared.service";
import {CategoryService} from "../../service/category.service";
import {Category} from "../../model/category";
import {UtilsService} from "../../service/utils.service";
import {PageEvent} from "@angular/material/paginator";
import {VotesService} from "../../service/votes.service";
import {Vote} from "../../model/vote";

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {
  ru: boolean;
  recipe: Recipe = new Recipe();
  recipes: Recipe[] = [];
  admin = false;
  author = false;
  favorite: Favorite = new Favorite();
  authenticated = false;
  category: Category = new Category();
  // MatPaginator Inputs
  length: number;
  pageSize = 8;
  pageSizeOptions: number[] = [8, 32, 64];
  // MatPaginator Output
  pageEvent: PageEvent;
  //votes
  vote: Vote = new Vote();

  constructor(private router: Router, private recipeService: RecipeService, private favoriteService: FavoriteService, private ss: SharedService,
              private categoryService: CategoryService, private utilsService: UtilsService, private votesService: VotesService) {
    if (localStorage.getItem('token') != undefined) {
      this.authenticated = true;
    }
  }

  ngOnInit() {
    this.recipeService.getCountAllRecipesInCategory(sessionStorage.getItem('categoryName')).subscribe(data => {
      this.length = data;
    });
    if (this.authenticated != false) {
      this.recipeService.getRecipesByCategoryName(sessionStorage.getItem('categoryName'), localStorage.getItem('id'),
        0, this.pageSize, "name").subscribe(data => {
        this.recipes = data;
      });
    } else {
      this.recipeService.getRecipesByCategoryName2(sessionStorage.getItem('categoryName'),
        0, this.pageSize, "name").subscribe(data => {
        this.recipes = data;
      });
    }

    let admin = localStorage.getItem('adminRole');
    this.admin = (admin == 'true');

    let author = localStorage.getItem('authorRole');
    this.author = (author == 'true');
    this.categoryService.getByName(sessionStorage.getItem('categoryName')).subscribe(data => {
      this.category = data;
    });
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => {
        this.ru = item;
      });
  }

  view(id: string) {
    sessionStorage.setItem('recipe', id);
    this.router.navigate(['recipe-view']);
  }

  addToFavorite(id: string) {
    this.favoriteService.addToFavorite(id, this.authenticated);
    if (this.authenticated != false) {
      for (let i = 0; i < this.recipes.length; i++) {
        if (this.recipes[i].id == id) {
          this.recipes[i].inFavorite = true;
        }
      }
    }
  }

  addRecipe() {
    if (this.author != false || this.admin != false) {
      this.router.navigate(['addRecipe']);
    } else this.utilsService.alert("author or admin");
  }

  deleteFromFavorite(recipeId: string) {
    this.favoriteService.deleteByUserAndRecipeId(localStorage.getItem('id'), recipeId).subscribe(data => {
      for (let i = 0; i < this.recipes.length; i++) {
        if (this.recipes[i].id == recipeId) {
          this.recipes[i].inFavorite = false;
        }
      }
    });
  }

  getServerData(event ?: PageEvent) {
    if (this.authenticated != false) {
      this.recipeService.getRecipesByCategoryName(sessionStorage.getItem('categoryName'), localStorage.getItem('id'),
        event.pageIndex, event.pageSize, "name").subscribe(
        response => {
          this.recipes = response;
        }
      );
    } else {
      this.recipeService.getRecipesByCategoryName2(sessionStorage.getItem('categoryName'), event.pageIndex,
        event.pageSize, "name").subscribe(
        response => {
          this.recipes = response;
        }
      );
    }
    return event;
  }

  like(id: string) {
    if (this.authenticated != false) {
      this.vote.recipeId = id;
      this.vote.userId = localStorage.getItem('id');
      this.vote.positiveVote = true;
      this.votesService.createVote(this.vote).subscribe(data => {
        if (data != null) {

        } else this.utilsService.alert("evaluated")
      })
    } else this.utilsService.alert("you need to authenticated");
  }

  dislike(id: string) {
    if (this.authenticated != false) {
      this.vote.recipeId = id;
      this.vote.userId = localStorage.getItem('id');
      this.vote.negativeVote = true;
      this.votesService.createVote(this.vote).subscribe(data => {
        if (data != null) {

        } else this.utilsService.alert("evaluated")
      });
    } else this.utilsService.alert("you need to authenticated");
  }
}
