import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RecipeService} from "../../service/recipe.service";
import {CreateRecipeDTO} from "../../model/createRecipe/create-recipe-dto";
import {IngredientRecipeDTO} from "../../model/createRecipe/ingredient-recipe-dto";
import {CategoryRecipeDTO} from "../../model/createRecipe/category-recipe-dto";
import {CategoryService} from "../../service/category.service";

@Component({
  selector: 'app-recipe-add',
  templateUrl: './recipe-add.component.html',
  styleUrls: ['./recipe-add.component.css']
})
export class RecipeAddComponent implements OnInit {
  submitted = false;
  createRecipeDTO: CreateRecipeDTO;
  first = true;
  second = false;
  third = false;
  ingredients: IngredientRecipeDTO[] = [];
  ingredient: IngredientRecipeDTO = new IngredientRecipeDTO();
  categories: CategoryRecipeDTO[];
  category: CategoryRecipeDTO = new CategoryRecipeDTO();
  checkedArray: CategoryRecipeDTO[] = [];


  constructor(private route: ActivatedRoute, private router: Router, private recipeService: RecipeService,
              private categoryService: CategoryService) {
    this.createRecipeDTO = new CreateRecipeDTO();
  }

  ngOnInit() {
    this.categoryService.findAllCategoriesDTO().subscribe(data => {
      this.categories = data;
      this.first = true;
      this.second = false;
    });
  }

  toDescription() {
    this.first = true;
    this.second = false;
    this.third = false;
  }

  toIngredient(name: string, difficulty: string, time: number) {
    this.first = false;
    this.second = true;
    this.third = false;

    this.createRecipeDTO.name = name;
    this.createRecipeDTO.cookingDifficulty = difficulty;
    this.createRecipeDTO.cookingTime = time;
    this.createRecipeDTO.authorId = localStorage.getItem("id");
    this.createRecipeDTO.categoryRecipeDTOList = this.checkedArray;
  }

  toCooking() {
    this.first = false;
    this.second = false;
    this.third = true;
  }

  checkArray(category: CategoryRecipeDTO) {
    this.checkedArray.push(category);
  }

  addIngredient() {
    let ingredient = new IngredientRecipeDTO();
    ingredient.name = (<HTMLInputElement>document.getElementById("ingredientName")).value;
    ingredient.amount = +(<HTMLInputElement>document.getElementById("amount")).value;
    ingredient.unit = (<HTMLInputElement>document.getElementById("unit")).value;
    ingredient.note = (<HTMLInputElement>document.getElementById("note")).value;
    this.ingredients.push(ingredient);
    this.createRecipeDTO.ingredientRecipeDTOList = this.ingredients;

  }

  deleteIngredient() {
    window.alert("[cde");
  }

  addCookingStep() {
    let div = document.getElementById("cookingStep");
    let div2 = div.cloneNode(true);
    div.parentNode.insertBefore(div2, div);

    document.getElementsByName("c.name")[0].textContent = "test";
    document.getElementsByName("c.description")[0].textContent = "";
    document.getElementsByName("c.number")[0].textContent = "1";
  }

}
