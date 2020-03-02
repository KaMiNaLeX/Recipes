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
import {Recipe} from "../../model/recipe";
import {Unit} from "../../model/unit.enum";
import {SharedService} from "../../service/shared.service";

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
  unit = [];
  allIngredients: Ingredient[];
  recipe: Recipe;
  primaryName: String;

  selectedFile2: File[] = [];
  imgURL2: any;

  selectedFile: File = null;
  imgURL: any;

  ru: boolean;

  constructor(private route: ActivatedRoute, private router: Router, private recipeService: RecipeService,
              private categoryService: CategoryService, private ingredientService: IngredientService, private ss: SharedService) {
  }

  ngOnInit() {
    this.recipeService.getById(sessionStorage.getItem('recipe')).subscribe(
      data => {
        this.createRecipeDTO = data;
        this.primaryName = this.createRecipeDTO.name;
      }
    );
    let u = Unit;
    this.unit = Object.values(u);
    this.first = true;
    this.second = false;
    this.categoryService.findAllCategoriesDTO().subscribe(data => {
      this.categories = data;
    });
    this.ingredientService.findAll(0, 10, "name").subscribe(data => this.allIngredients = data);

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
    if (name != null && name != this.primaryName) {
      this.recipeService.getByNameAndAuthor(name, localStorage.getItem('id')).subscribe(data => {
        this.recipe = data;
        if (this.recipe != null) {
          (<HTMLInputElement>document.getElementById('name')).value = null;
          this.createRecipeDTO.name = null;
          window.alert('You already have recipe with this name!');
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
    step.imgSource = this.imgURL2;
    this.cookingSteps.push(step);
  }

  handleFileInput2(event) {
    console.log(event);
    this.selectedFile2.push(event.target.files[0]);
    // Below part is used to display the selected image
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = (event2) => {
      this.imgURL2 = reader.result;
    }
  }

  handleFileInput(event) {
    console.log(event);
    this.selectedFile = event.target.files[0];
    // Below part is used to display the selected image
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = (event2) => {
      this.imgURL = reader.result;
    }
  }

  onSubmit() {
    let stepTable = (<HTMLTableElement>document.getElementById('stepTable')).rows.length;
    if (stepTable == 1) {
      window.alert('Add at least one step');
    }
    let ingTable = (<HTMLTableElement>document.getElementById('ingredientTable')).rows.length;
    if (ingTable == 1) {
      window.alert('Add at least one ingredient');
    }
    let y = -1;
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
      if (this.imgURL2 != undefined) {
        for (let i = 0; i < this.cookingSteps.length; i++) {
          if (this.cookingSteps[i].imgSource != null) {
            if (this.cookingSteps[i].imgSource.length > 1000) {
              this.cookingSteps[i].imgSource = "pic";
            }
          }
        }
        this.createRecipeDTO.cookingStepRecipeDTOList = this.cookingSteps;
      }
      this.recipeService.update(this.createRecipeDTO.id, this.createRecipeDTO).subscribe(data => {
          this.recipeService.getById(this.createRecipeDTO.id).subscribe(data => {
              this.createRecipeDTO = data;
            console.log(this.createRecipeDTO.cookingStepRecipeDTOList[0].id);
              if (this.imgURL != undefined) {
                this.recipeService.addPhoto4Recipe(this.createRecipeDTO.id, this.selectedFile);
              }
              if (this.imgURL2 != undefined) {
                for (let i = 0; i < this.createRecipeDTO.cookingStepRecipeDTOList.length; i++) {
                  console.log(this.createRecipeDTO.cookingStepRecipeDTOList[i].id);
                  if (this.createRecipeDTO.cookingStepRecipeDTOList[i].imgSource != null &&
                    this.createRecipeDTO.cookingStepRecipeDTOList[i].imgSource == "pic") {
                    this.recipeService.addPhoto4Step(this.createRecipeDTO.cookingStepRecipeDTOList[i].id,
                      this.selectedFile2[y += 1]);
                  }
                }
              }
              this.first = true;
              this.second = false;
              this.third = false;
              window.location.reload();
            }
          );

        }
      );

      //this.router.navigate(['recipe']);
      window.alert(
        "Recipe is updated!"
      );
    } else {
      window.alert("Please fill in all fields!");
    }
  }
}
