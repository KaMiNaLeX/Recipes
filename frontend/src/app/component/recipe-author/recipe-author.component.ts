import {Component, OnInit} from '@angular/core';
import {RecipeService} from "../../service/recipe.service";
import {CreateRecipeDTO} from "../../model/createRecipe/create-recipe-dto";
import {Router} from "@angular/router";

@Component({
  selector: 'app-recipe-author',
  templateUrl: './recipe-author.component.html',
  styleUrls: ['./recipe-author.component.css']
})
export class RecipeAuthorComponent implements OnInit {
  recipes: CreateRecipeDTO[];

  constructor(private router: Router, private recipeService: RecipeService) {
  }

  ngOnInit() {
    this.recipeService.getByAuthorId(localStorage.getItem('id')).subscribe(data => this.recipes = data);
  }

  view(id: string) {
    sessionStorage.setItem('recipe', id);
    this.router.navigate(['recipe-view']);
  }

}
