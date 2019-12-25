import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RecipeService} from "../../service/recipe.service";
import {CategoryService} from "../../service/category.service";
import {IngredientService} from "../../service/ingredient.service";
import {Recipe} from "../../model/recipe";
import {User} from "../../model/user";
import {Ingredient} from "../../model/ingredient";
import {IngredientRecipeDTO} from "../../model/createRecipe/ingredient-recipe-dto";
import {TypeIngredient} from "../../model/type-ingredient.enum";
import {IngredientNameDTO} from "../../model/findByIngredients/ingredient-name-dto";
import {IngredientNameListDTO} from "../../model/findByIngredients/ingredient-name-list-dto";
import {RecipeDataDTO} from "../../model/findByData/recipe-data-dto";
import {CookingDifficultyDTO} from "../../model/findByData/cooking-difficulty-dto";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  first = true;
  second = false;
  third = false;
  typeDiv = false;
  recipeDiv = false;
  recipes: Recipe[];
  recipe: Recipe = new Recipe();
  author: User = new User();
  allIngredients: Ingredient[];
  ingredient: IngredientRecipeDTO = new IngredientRecipeDTO();
  keys = [];
  typeIngredients: Ingredient[];
  ingredientNameDTOList: IngredientNameListDTO = new IngredientNameListDTO();
  ingredientNameDTO: IngredientNameDTO[] = [];
  recipeData: RecipeDataDTO = new RecipeDataDTO();

  constructor(private route: ActivatedRoute, private router: Router, private recipeService: RecipeService,
              private categoryService: CategoryService, private ingredientService: IngredientService) {
  }

  ngOnInit() {
    this.ingredientService.findAll().subscribe(data => this.allIngredients = data);
  }

  byName() {
    this.first = true;
    this.second = false;
    this.third = false;
  }

  byIngredients() {
    this.first = false;
    this.second = true;
    this.third = false;
    let typeIngredient = TypeIngredient;
    this.keys = Object.values(typeIngredient);
  }

  byRecipes() {
    this.first = false;
    this.second = false;
    this.third = true;
  }

  searchByName(name: string) {
    this.recipeService.getByName(name).subscribe(data => this.recipes = data);
    this.recipeDiv = true;
  }

  searchByAuthorName(name: string) {
    this.recipeService.getByAuthorName(name).subscribe(data => this.recipes = data);
    this.recipeDiv = true;
  }

  view(id: string) {
    sessionStorage.setItem('recipe', id);
    this.router.navigate(['recipe-view']);
  }

  findIngredientByType(type: TypeIngredient) {
    this.ingredientService.findAllByType(type).subscribe(data => this.typeIngredients = data);
    this.typeDiv = true;
  }

  addSelectIngredient(ingredient: string) {
    let ingredientNameDTO = new IngredientNameDTO();
    ingredientNameDTO.name = ingredient;
    let startLength = this.ingredientNameDTO.length;
    if (this.ingredientNameDTO.length != 0) {
      for (let i = 0; i < this.ingredientNameDTO.length; i++) {
        let ingredientInArray = new IngredientNameDTO();
        ingredientInArray = this.ingredientNameDTO[i];
        if (ingredientInArray.name == ingredientNameDTO.name) {
          this.ingredientNameDTO.splice(i, 1);
          console.log(this.ingredientNameDTO);
        }
      }
      if (startLength == this.ingredientNameDTO.length) {
        this.ingredientNameDTO.push(ingredientNameDTO);
        console.log(this.ingredientNameDTO);
      }
    } else {
      this.ingredientNameDTO.push(ingredientNameDTO);
      console.log(this.ingredientNameDTO);
    }

  }

  deleteFromCart(name: string) {
    for (let i = 0; i < this.ingredientNameDTO.length; i++) {
      let ingredientInArray = new IngredientNameDTO();
      ingredientInArray = this.ingredientNameDTO[i];
      if (ingredientInArray.name == name) {
        this.ingredientNameDTO.splice(i, 1);
        console.log(this.ingredientNameDTO);
      }
    }
  }

  searchByData(time: number) {
    let easy = (<HTMLInputElement>document.getElementById('Checkbox1'));
    let medium = (<HTMLInputElement>document.getElementById('Checkbox2'));
    let hard = (<HTMLInputElement>document.getElementById('Checkbox3'));
    let cookingDifficultyDTOList = new Array<CookingDifficultyDTO>();
    let checkedEasy = (easy.checked == true);
    if (checkedEasy != false) {
      let dif = new CookingDifficultyDTO();
      dif.cookingDifficulty = easy.value;
      cookingDifficultyDTOList.push(dif);
    }
    let checkedMedium = (medium.checked == true);
    if (checkedMedium != false) {
      let dif = new CookingDifficultyDTO();
      dif.cookingDifficulty = medium.value;
      cookingDifficultyDTOList.push(dif);
    }
    let checkedHard = (hard.checked == true);
    if (checkedHard != false) {
      let dif = new CookingDifficultyDTO();
      dif.cookingDifficulty = hard.value;
      cookingDifficultyDTOList.push(dif);
    }

    if (time == undefined) {
      time = 0;
    }

    this.recipeData.cookingTime = +time;
    this.recipeData.category = "";
    this.recipeData.includeMeat = true;
    this.recipeData.cookingDifficultyDTOList = cookingDifficultyDTOList;

    this.recipeService.findAllByData(this.recipeData).subscribe(data => this.recipes = data);
    this.recipeDiv = true;
  }


  searchByIngredients() {
    this.ingredientNameDTOList.ingredientNameDTOList = this.ingredientNameDTO;
    this.recipeService.findAllByIngredients(this.ingredientNameDTOList).subscribe(data => this.recipes = data);
    this.recipeDiv = true;
  }
}
