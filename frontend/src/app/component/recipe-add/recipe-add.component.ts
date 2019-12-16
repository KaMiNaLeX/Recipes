import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RecipeService} from "../../service/recipe.service";
import {CreateRecipeDTO} from "../../model/createRecipe/create-recipe-dto";

@Component({
  selector: 'app-recipe-add',
  templateUrl: './recipe-add.component.html',
  styleUrls: ['./recipe-add.component.css']
})
export class RecipeAddComponent implements OnInit {

  createRecipeDTO: CreateRecipeDTO;
  first = true;
  second = false;
  third = false;

  constructor(private route: ActivatedRoute, private router: Router, private recipeService: RecipeService) {
    this.createRecipeDTO = new CreateRecipeDTO();
  }

  ngOnInit() {
  }

  toDescription() {
    this.first = true;
    this.second = false;
    this.third = false;
  }

  toIngredient() {
    this.first = false;
    this.second = true;
    this.third = false;
  }

  toCooking() {
    this.first = false;
    this.second = false;
    this.third = true;
  }

}
