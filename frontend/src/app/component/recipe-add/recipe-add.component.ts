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

  createRecipeDTO: CreateRecipeDTO;
  first = true;
  second = false;
  third = false;
  ingredients: IngredientRecipeDTO[] = [];
  ingredient: IngredientRecipeDTO = new IngredientRecipeDTO();
  categories: CategoryRecipeDTO[];

  constructor(private route: ActivatedRoute, private router: Router, private recipeService: RecipeService,
              private categoryService: CategoryService) {
    this.createRecipeDTO = new CreateRecipeDTO();
  }

  ngOnInit() {
    this.categoryService.findAllCategoriesDTO().subscribe(data => {
      this.categories = data
    });
  }

  toDescription() {
    this.first = true;
    this.second = false;
    this.third = false;
  }

  toIngredient(name: string, difficulty: string, time: number, category: Array<CategoryRecipeDTO>) {
    this.first = false;
    this.second = true;
    this.third = false;
  }

  toCooking() {
    this.first = false;
    this.second = false;
    this.third = true;
  }

  addIngredient(name: string, amount: number, unit: string, note: string) {
    this.ingredient.name = name;
    this.ingredient.amount = amount;
    this.ingredient.unit = unit;
    this.ingredient.note = note;
    this.ingredients.push(this.ingredient);


    let div = document.getElementById("ingredient");
    let count = document.getElementsByName("ingredient").length;
    let div2 = div.cloneNode(true);
    div.id += (count);
    div.parentNode.insertBefore(div2, div);

    let emptyDiv = document.getElementById("ingredient1");
    emptyDiv.hidden = true;

    document.getElementsByName("i.name")[0].textContent = this.ingredient.name;
    document.getElementsByName("i.amount")[0].textContent = this.ingredient.amount.toString();
    document.getElementsByName("i.unit")[0].textContent = this.ingredient.unit;
    document.getElementsByName("i.note")[0].textContent = this.ingredient.note;
  }

  deleteIngredient() {
    window.alert("[cde");
  }

  addCookingStep() {
    let div = document.getElementById("cookingStep");
    let div2 = div.cloneNode(true);
    div.parentNode.insertBefore(div2, div);

    document.getElementsByName("c.name")[0].textContent = "test";
    document.getElementsByName("c.description")[0].textContent = "lexaloil ooooooooooo ooooooooooooooooo ooooooooooooo ooooooooooooooo oooooooooooooo oooooooooooooooooooooooo ooooooooooooooooo ooooooooooooooooo oooooooooooooo oooooooooooo ooooooooo ooooox";
    document.getElementsByName("c.number")[0].textContent = "1";
  }

}
