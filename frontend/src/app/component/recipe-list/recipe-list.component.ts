import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RecipeService} from "../../service/recipe.service";
import {Recipe} from "../../model/recipe";

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {

  recipe: Recipe = new Recipe();
  recipes: Recipe[];
  admin = false;
  author = false;

  constructor(private router: Router, private recipeService: RecipeService) {
  }

  ngOnInit() {
    this.recipeService.getRecipesByCategoryName(sessionStorage.getItem('categoryName')).subscribe(data => {
      this.recipes = data;
    });
    let admin = localStorage.getItem('adminRole');
    this.admin = (admin == 'true');

    let author = localStorage.getItem('authorRole');
    this.author = (author == 'true');

  }

  view(id: string) {
    sessionStorage.setItem('recipe', id);
    this.router.navigate(['recipe-view']);
    window.alert(id);
  }

  addRecipe() {
    if (this.author != false || this.admin != false) {
      this.router.navigate(['addRecipe']);
    } else window.alert("You need have AUTHOR or ADMIN role");

  }


}
