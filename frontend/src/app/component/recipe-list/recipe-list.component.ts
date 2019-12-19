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

  constructor(private router: Router, private recipeService: RecipeService) {
  }

  ngOnInit() {
    this.recipeService.getRecipesByCategoryName(sessionStorage.getItem('categoryName')).subscribe(data => {
      this.recipes = data;
    });
  }

  view(id: string) {
    sessionStorage.setItem('recipe', id);
    this.router.navigate(['recipe-view']);
    window.alert(id);
  }


}
