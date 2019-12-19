import {Component, OnInit} from '@angular/core';
import {CreateRecipeDTO} from "../../model/createRecipe/create-recipe-dto";
import {RecipeService} from "../../service/recipe.service";

@Component({
  selector: 'app-recipe-view',
  templateUrl: './recipe-view.component.html',
  styleUrls: ['./recipe-view.component.css']
})
export class RecipeViewComponent implements OnInit {
  createRecipeDTO: CreateRecipeDTO = new CreateRecipeDTO();

  constructor(private createRecipeService: RecipeService) {
  }

  ngOnInit() {
    this.createRecipeService.getById(sessionStorage.getItem('recipe')).subscribe(
      data => this.createRecipeDTO = data);
  }

}
