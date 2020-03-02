import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RecipeService} from "../../service/recipe.service";
import {Recipe} from "../../model/recipe";
import {FavoriteService} from "../../service/favorite.service";
import {CreateFavorite} from "../../model/create-favorite";
import {Favorite} from "../../model/favorite";
import {User} from "../../model/user";
import {SharedService} from "../../service/shared.service";
import {CategoryService} from "../../service/category.service";
import {Category} from "../../model/category";

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {
  ru: boolean;
  recipe: Recipe = new Recipe();
  recipes: Recipe[];
  admin = false;
  author = false;
  createFavorite: CreateFavorite = new CreateFavorite();
  favorite: Favorite = new Favorite();
  authenticated = false;
  category: Category = new Category();

  constructor(private router: Router, private recipeService: RecipeService, private favoriteService: FavoriteService, private ss: SharedService,
              private categoryService: CategoryService) {
    if (localStorage.getItem('token') != undefined) {
      this.authenticated = true;
    }
  }

  ngOnInit() {
    this.recipeService.getRecipesByCategoryName(sessionStorage.getItem('categoryName'), 0, 10, "name").subscribe(data => {
      this.recipes = data;
      if (this.recipes != null) {
        if (this.recipes[0].id != null) {
          for (let i = 0; i < this.recipes.length; i++) {
            this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
              this.recipes[i].authorName = data.login;
            })
          }
        }
      }
    });
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
    if (this.authenticated != false) {
      this.createFavorite.recipeId = id;
      this.createFavorite.userId = localStorage.getItem('id');
      this.favoriteService.create(this.createFavorite).subscribe(data => {
        this.favorite = data;
        if (this.favorite == null) {
          window.alert("This recipe is already in favorites!");
        } else {
          window.alert("Recipe add to favorites");
        }
      })
    } else window.alert("You need to authenticated!")

  }

  addRecipe() {
    if (this.author != false || this.admin != false) {
      this.router.navigate(['addRecipe']);
    } else window.alert("You need have AUTHOR or ADMIN role");
  }
}
