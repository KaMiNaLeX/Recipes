import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RecipeService} from "../../service/recipe.service";
import {CreateRecipeDTO} from "../../model/createRecipe/create-recipe-dto";
import {IngredientRecipeDTO} from "../../model/createRecipe/ingredient-recipe-dto";

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
  ingredients: IngredientRecipeDTO[];
  ingredient: IngredientRecipeDTO = new IngredientRecipeDTO();

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

  addIngredient() {
    this.ingredient.name = "test";
    this.ingredient.unit = "test";
    this.ingredient.amount = 100;
    this.ingredient.note = "";


    let div = document.getElementById("ingredient");
    let div2 = div.cloneNode(true);
    div.parentNode.insertBefore(div2, div);

    let count = document.getElementsByName("delete").length;
    let deleteButton = document.getElementById("delete");
    deleteButton.hidden = false;
    let d = document.getElementsByName("delete")[count - 1];
    d.hidden = true;

    div.id = "ingredient" + (count - 1);

    document.getElementsByName("i.name")[0].textContent = this.ingredient.name;
    document.getElementsByName("i.amount")[0].textContent = this.ingredient.amount.toString();
    document.getElementsByName("i.unit")[0].textContent = this.ingredient.unit;
  }

  deleteIngredient() {
    window.alert("[cde");
  }

  addCookingStep() {
    let div = document.getElementById("cookingStep");
    let div2 = div.cloneNode(true);
    div.parentNode.insertBefore(div2, div);

    document.getElementsByName("c.name")[0].textContent = "test";
    document.getElementsByName("c.description")[0].textContent = "test";
    document.getElementsByName("c.number")[0].textContent = "1";
  }

}
