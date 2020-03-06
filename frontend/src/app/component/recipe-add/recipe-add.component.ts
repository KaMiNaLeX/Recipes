import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RecipeService} from "../../service/recipe.service";
import {CreateRecipeDTO} from "../../model/createRecipe/create-recipe-dto";
import {IngredientRecipeDTO} from "../../model/createRecipe/ingredient-recipe-dto";
import {CategoryRecipeDTO} from "../../model/createRecipe/category-recipe-dto";
import {CategoryService} from "../../service/category.service";
import {CookingStepRecipeDTO} from "../../model/createRecipe/cooking-step-recipe-dto";
import {IngredientService} from "../../service/ingredient.service";
import {Ingredient} from "../../model/ingredient";
import {Unit} from "../../model/unit.enum";
import {Recipe} from "../../model/recipe";
import {SharedService} from "../../service/shared.service";
import {UtilsService} from "../../service/utils.service";

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
  category: CategoryRecipeDTO = new CategoryRecipeDTO();
  checkedArray: CategoryRecipeDTO[] = [];
  cookingStep: CookingStepRecipeDTO = new CookingStepRecipeDTO();
  cookingSteps: CookingStepRecipeDTO[] = [];
  unit = [];
  allIngredients: Ingredient[];
  recipe: Recipe;

  selectedFile: File[] = [];
  imgURL: any;

  selectedFile2: File = null;
  imgURL2: any;

  ru: boolean;

  constructor(private route: ActivatedRoute, private router: Router, private recipeService: RecipeService,
              private categoryService: CategoryService, private ingredientService: IngredientService, private ss: SharedService,
              private utilsService: UtilsService) {
    this.createRecipeDTO = new CreateRecipeDTO();

  }

  ngOnInit() {
    let u = Unit;
    this.unit = Object.values(u);
    this.first = true;
    this.second = false;
    this.categoryService.findAllCategoriesDTO().subscribe(data => {
      this.categories = data;
    });
    this.ingredientService.findAll(0, 10, "name").subscribe(data => this.allIngredients = data);
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => this.ru = item);
  }

  toDescription() {
    this.first = true;
    this.second = false;
    this.third = false;
  }

  checkRecipe() {
    let name = (<HTMLInputElement>document.getElementById('name')).value;
    if (name != null) {
      this.recipeService.getByNameAndAuthor(name, localStorage.getItem('id')).subscribe(data => {
        this.recipe = data;
        if (this.recipe != null) {
          (<HTMLInputElement>document.getElementById('name')).value = null;
          this.createRecipeDTO.name = null;
          this.utilsService.alert("have recipe with this name");
        }
      })
    }
  }

  toIngredient(name: string, difficulty: string, time: number) {
    this.first = false;
    this.second = true;
    this.third = false;

    this.createRecipeDTO.name = name;
    this.createRecipeDTO.cookingDifficulty = difficulty;
    this.createRecipeDTO.cookingTime = time;
  }

  toCooking() {
    this.first = false;
    this.second = false;
    this.third = true;
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
      }
    }
  }

  deleteStep(stepName: string) {
    for (let i = 0; i < this.cookingSteps.length; i++) {
      let step = new CookingStepRecipeDTO();
      step = this.cookingSteps[i];
      if (step.name == stepName) {
        this.cookingSteps.splice(i, 1);
      }
    }
  }

  addCookingStep(name: string, description: string, number: number) {
    let step = new CookingStepRecipeDTO();
    step.name = name;
    step.description = description;
    step.number = number + 1;
    step.active = true;
    step.imgSource = this.imgURL;
    this.cookingSteps.push(step);
  }

  handleFileInput(event) {
    console.log(event);
    this.selectedFile.push(event.target.files[0]);

    // Below part is used to display the selected image
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = (event2) => {
      this.imgURL = reader.result;
    }
  }

  handleFileInput2(event) {
    console.log(event);
    this.selectedFile2 = event.target.files[0];
    // Below part is used to display the selected image
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = (event2) => {
      this.imgURL2 = reader.result;
    }
  }

  onSubmit() {
    let stepTable = (<HTMLTableElement>document.getElementById('stepTable')).rows.length;
    if (stepTable == 1) {
      this.utilsService.alert("add one step");
    }
    let ingTable = (<HTMLTableElement>document.getElementById('ingredientTable')).rows.length;
    if (ingTable == 1) {
      this.utilsService.alert('add one ingredient');
    }
    this.createRecipeDTO.imgSource = null;
    for (let i = 0; i < this.cookingSteps.length; i++) {
      this.cookingSteps[i].imgSource = null;
    }
    this.createRecipeDTO.cookingStepRecipeDTOList = this.cookingSteps;
    this.createRecipeDTO.ingredientRecipeDTOList = this.ingredients;
    this.createRecipeDTO.authorId = localStorage.getItem("id");
    this.createRecipeDTO.categoryRecipeDTOList = this.checkedArray;
    if (this.createRecipeDTO.cookingStepRecipeDTOList.length != 0 &&
      this.createRecipeDTO.ingredientRecipeDTOList.length != 0 &&
      this.createRecipeDTO.categoryRecipeDTOList.length != 0 &&
      this.createRecipeDTO.cookingTime != null &&
      this.createRecipeDTO.name != null &&
      this.createRecipeDTO.cookingDifficulty != null) {
      this.recipeService.createRecipe(this.createRecipeDTO).subscribe(data => {
        this.createRecipeDTO = data;
        this.recipeService.addPhoto4Recipe(this.createRecipeDTO.id, this.selectedFile2);
        this.cookingSteps = [];
        for (let i = 0; i < this.createRecipeDTO.cookingStepRecipeDTOList.length; i++) {
          this.cookingSteps.push(this.createRecipeDTO.cookingStepRecipeDTOList[i]);
          console.log(this.cookingSteps[i]);
          this.recipeService.addPhoto4Step(this.createRecipeDTO.cookingStepRecipeDTOList[i].id, this.selectedFile[i]);
        }
      });
      this.router.navigate(['category']);
      this.utilsService.alert("recipe created");
    } else {
      this.utilsService.alert("fill all fields");
    }
  }
}


