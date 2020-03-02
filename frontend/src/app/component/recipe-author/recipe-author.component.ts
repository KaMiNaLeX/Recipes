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
  recipes: CreateRecipeDTO[] = [];
  recipe: CreateRecipeDTO = new CreateRecipeDTO();

  constructor(private router: Router, private recipeService: RecipeService) {
  }

  ngOnInit() {
    this.recipeService.getByAuthorId(localStorage.getItem('id'),0, 10, "name").subscribe(data => this.recipes = data);
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
    this.router.navigate(['recipe-add']);
  }

  delete(id: string) {

    this.recipeService.delete(id)
      .subscribe(
        data => {
          console.log(data);
          this.recipeService.getByAuthorId(localStorage.getItem('id'),0, 10, "name").subscribe(data => {
            this.recipes = data;
          })
        },
        error => console.log(error));
    window.alert("Recipe is deleted!");
  }

}
