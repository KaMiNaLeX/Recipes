import {Component, OnInit} from '@angular/core';
import {CreateRecipeDTO} from "../../model/createRecipe/create-recipe-dto";
import {RecipeService} from "../../service/recipe.service";
import {SharedService} from "../../service/shared.service";

@Component({
  selector: 'app-recipe-view',
  templateUrl: './recipe-view.component.html',
  styleUrls: ['./recipe-view.component.css']
})
export class RecipeViewComponent implements OnInit {
  createRecipeDTO: CreateRecipeDTO = new CreateRecipeDTO();
  ru: boolean;

  constructor(private createRecipeService: RecipeService, private ss: SharedService) {
  }

  ngOnInit() {
    this.createRecipeService.getById(sessionStorage.getItem('recipe')).subscribe(
      data => this.createRecipeDTO = data);
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => this.ru = item);
  }

}
