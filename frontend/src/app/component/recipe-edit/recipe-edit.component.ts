import {Component, OnInit} from '@angular/core';
import {CreateRecipeDTO} from "../../model/createRecipe/create-recipe-dto";
import {RecipeService} from "../../service/recipe.service";
import {IngredientRecipeDTO} from "../../model/createRecipe/ingredient-recipe-dto";
import {CategoryRecipeDTO} from "../../model/createRecipe/category-recipe-dto";
import {CookingStepRecipeDTO} from "../../model/createRecipe/cooking-step-recipe-dto";
import {Ingredient} from "../../model/ingredient";
import {ActivatedRoute, Router} from "@angular/router";
import {CategoryService} from "../../service/category.service";
import {IngredientService} from "../../service/ingredient.service";

@Component({
  selector: 'app-recipe-edit',
  templateUrl: './recipe-edit.component.html',
  styleUrls: ['./recipe-edit.component.css']
})
export class RecipeEditComponent implements OnInit {

  createRecipeDTO: CreateRecipeDTO = new CreateRecipeDTO();
  editCreateRecipeDTO: CreateRecipeDTO;
  first = true;
  second = false;
  third = false;
  ingredients: IngredientRecipeDTO[] = [];
  ingredient: IngredientRecipeDTO = new IngredientRecipeDTO();
  categories: CategoryRecipeDTO[];
  category: CategoryRecipeDTO = new CategoryRecipeDTO();
  checkedArray: CategoryRecipeDTO[] = [];
  cookingStep: CookingStepRecipeDTO = new CookingStepRecipeDTO();
  cookingSteps: CookingStepRecipeDTO[] = [];
  unit: String[] = [];
  allIngredients: Ingredient[];

  constructor(private route: ActivatedRoute, private router: Router, private recipeService: RecipeService,
              private categoryService: CategoryService, private ingredientService: IngredientService) {
  }

  ngOnInit() {
    this.recipeService.getById(sessionStorage.getItem('recipe')).subscribe(
      data => this.createRecipeDTO = data);

    this.unit.push("PIECE", "GRAM", "MML", "KG", "L", "TEASPOON", "TABLESPOON", "CUP", "LEAF", "BY_TASTE");
    this.first = true;
    this.second = false;
    this.categoryService.findAllCategoriesDTO().subscribe(data => {
      this.categories = data;
    });
    this.ingredientService.findAll().subscribe(data => this.allIngredients = data);

    //set checked radiobutton
    let radioButton;
    let difficult = this.createRecipeDTO.cookingDifficulty;
    if (difficult == 'EASY') {
      radioButton = document.getElementById('exampleRadios1');
      radioButton.checked = true;
    } else if (difficult == 'MEDIUM') {
      radioButton = document.getElementById('exampleRadios2');
      radioButton.checked = true;
    } else if (difficult == 'HARD') {
      radioButton = document.getElementById('exampleRadios3');
      radioButton.checked = true;
    }
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
    this.ingredients = this.createRecipeDTO.ingredientRecipeDTOList;
    if (this.ingredients.length >= this.createRecipeDTO.ingredientRecipeDTOList.length) {
      this.createRecipeDTO.ingredientRecipeDTOList = this.ingredients;
    }
  }

  toCooking() {
    this.first = false;
    this.second = false;
    this.third = true;
    this.cookingSteps = this.createRecipeDTO.cookingStepRecipeDTOList;
    if (this.cookingSteps.length >= this.createRecipeDTO.cookingStepRecipeDTOList.length) {
      this.createRecipeDTO.cookingStepRecipeDTOList = this.cookingSteps;
    }
  }

  checkArray(category: CategoryRecipeDTO) {
    this.checkedArray.push(category);
  }

  addIngredient(name: string, amount: number, unit: string, note: string) {
    let ingredient = new IngredientRecipeDTO();
    ingredient.name = name;
    ingredient.amount = amount;
    ingredient.unit = unit;
    ingredient.note = note;
    this.ingredients.push(ingredient);
  }

  deleteIngredient(ingredientName: string) {
    for (let i = 0; i < this.ingredients.length; i++) {
      let ingredient = new IngredientRecipeDTO();
      ingredient = this.ingredients[i];
      if (ingredient.name == ingredientName) {
        this.ingredients.splice(i, 1);
        this.createRecipeDTO.ingredientRecipeDTOList = this.ingredients;
        window.alert("Delete");
      }
    }
  }

  deleteStep(stepName: string) {
    for (let i = 0; i < this.cookingSteps.length; i++) {
      let step = new CookingStepRecipeDTO();
      step = this.cookingSteps[i];
      if (step.name == stepName) {
        this.cookingSteps.splice(i, 1);
        this.createRecipeDTO.cookingStepRecipeDTOList = this.cookingSteps;
        window.alert("Delete");
      }
    }
  }

  addCookingStep(name: string, description: string, number: number) {
    let step = new CookingStepRecipeDTO();
    step.name = name;
    step.description = description;
    step.number = number + 1;
    step.active = true;
    this.cookingSteps.push(step);
  }

  onSubmit() {
    let date: Date = new Date();
    this.createRecipeDTO.cookingStepRecipeDTOList = this.cookingSteps;
    this.createRecipeDTO.ingredientRecipeDTOList = this.ingredients;
    this.createRecipeDTO.authorId = localStorage.getItem("id");
    this.createRecipeDTO.categoryRecipeDTOList = this.checkedArray;
    this.createRecipeDTO.lastModified = date;
    if (this.createRecipeDTO.cookingStepRecipeDTOList.length != 0 &&
      this.createRecipeDTO.ingredientRecipeDTOList.length != 0 &&
      this.createRecipeDTO.categoryRecipeDTOList.length != 0 &&
      this.createRecipeDTO.cookingTime != null &&
      this.createRecipeDTO.name != null &&
      this.createRecipeDTO.cookingDifficulty != null) {
      this.recipeService.update(this.createRecipeDTO.id, this.createRecipeDTO).subscribe(data => {
        this.recipeService.getById(sessionStorage.getItem('recipe')).subscribe(data => {
          this.createRecipeDTO = data;
          this.first = true;
          this.second = false;
          this.third = false;
        })
      });
      //this.router.navigate(['recipe']);
      window.alert("Recipe is updated!")
    } else {
      window.alert("Please fill in all fields!")
    }
  }

}
