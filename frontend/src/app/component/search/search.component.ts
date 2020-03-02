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
import {CreateFavorite} from "../../model/create-favorite";
import {Favorite} from "../../model/favorite";
import {FavoriteService} from "../../service/favorite.service";
import {SharedService} from "../../service/shared.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  ru: boolean;
  first = true;
  second = false;
  third = false;
  typeDiv = false;
  recipeDiv = false;
  recipes: Recipe[];
  recipe: Recipe = new Recipe();
  author: User = new User();
  allIngredients: Ingredient[] = [];
  ingredient: IngredientRecipeDTO = new IngredientRecipeDTO();
  keys = [];
  typeIngredients: Ingredient[] = [];
  ingredientNameDTOList: IngredientNameListDTO = new IngredientNameListDTO();
  ingredientNameDTOS: IngredientNameDTO[] = [];
  recipeData: RecipeDataDTO = new RecipeDataDTO();
  createFavorite: CreateFavorite = new CreateFavorite();
  favorite: Favorite = new Favorite();
  authenticated = false;

  constructor(private route: ActivatedRoute, private router: Router, private recipeService: RecipeService,
              private categoryService: CategoryService, private ingredientService: IngredientService,
              private favoriteService: FavoriteService, private ss: SharedService) {
    if (localStorage.getItem('token') != undefined) {
      this.authenticated = true;
    }
  }

  ngOnInit() {
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => this.ru = item);
  }

  byName() {
    this.first = true;
    this.second = false;
    this.third = false;
  }

  byIngredients() {
    this.ingredientService.findAll(0, 10, "name").subscribe((data: Ingredient[]) => this.allIngredients = data);
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
    this.recipeService.getByName(name,0, 10, "name").subscribe(data => {
      this.recipes = data;
      for (let i = 0; i < this.recipes.length; i++) {
        this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
          this.recipes[i].authorName = data.login;
        })
      }
    });
    this.recipeDiv = true;
  }

  searchByAuthorName(name: string) {
    this.recipeService.getByAuthorName(name,0, 10, "name").subscribe(data => {
      this.recipes = data;
      for (let i = 0; i < this.recipes.length; i++) {
        this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
          this.recipes[i].authorName = data.login;
        })
      }
    });
    this.recipeDiv = true;
  }

  view(id: string) {
    sessionStorage.setItem('recipe', id);
    this.router.navigate(['recipe-view']);
  }

  findIngredientByType(type: TypeIngredient) {
    this.ingredientService.findAllByType(type).subscribe((name: IngredientNameDTO[]) => {
        for (let i = 0; i < name.length; i++) {
          this.ingredientNameDTOS.push(name[i]);
        }
        this.typeDiv = true;
      }
    );
  }

  addSelectIngredient(ingredient: Ingredient) {
    let ingredientNameDTO = new IngredientNameDTO();
    ingredientNameDTO.name = ingredient.name;
    if (ingredient.checked == false) {
      for (let i = 0; i < this.ingredientNameDTOS.length; i++) {
        let ingredientInArray = new IngredientNameDTO();
        ingredientInArray = this.ingredientNameDTOS[i];
        if (ingredientInArray.name == ingredientNameDTO.name) {
          this.ingredientNameDTOS.splice(i, 1);
          console.log(this.ingredientNameDTOS);
        }
      }
    }
    if (ingredient.checked == true) {
      this.ingredientNameDTOS.push(ingredientNameDTO);
    }

  }

  addToFavorite(id: string) {
    if (this.authenticated != false) {
      this.createFavorite.recipeId = id;
      this.createFavorite.userId = localStorage.getItem('id');
      this.favoriteService.create(this.createFavorite).subscribe(data => {
        this.favorite = data;
        if (this.favorite == null) {
          window.alert("This recipe is already in favorites!");
        } else {
          window.alert("Recipe add to favorites");
        }
      })
    } else window.alert("You need to authenticated!")

  }

  addToCart(name: string) {
    let ingredientNameDTO = new IngredientNameDTO();
    ingredientNameDTO.name = name;
    this.ingredientNameDTOS.push(ingredientNameDTO);
  }

  deleteFromCart(name: string) {
    for (let i = 0; i < this.ingredientNameDTOS.length; i++) {
      let ingredientInArray = new IngredientNameDTO();
      ingredientInArray = this.ingredientNameDTOS[i];
      if (ingredientInArray.name == name) {
        this.ingredientNameDTOS.splice(i, 1);
        this.typeIngredients[i].checked = false;
        console.log(this.ingredientNameDTOS);
      }
    }
    let select = (<HTMLSelectElement>document.getElementById('select'));
    if (select.value == name) {
      (<HTMLSelectElement>document.getElementById('select')).value = "Nothing to select";
    }
  }

  clearAll() {
    this.ingredientNameDTOS.splice(0, this.ingredientNameDTOS.length);
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

    this.recipeService.findAllByData(this.recipeData,0, 10, "name").subscribe(data => {
      this.recipes = data;
      for (let i = 0; i < this.recipes.length; i++) {
        this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
          this.recipes[i].authorName = data.login;
        })
      }
    });
    this.recipeDiv = true;
  }


  searchByIngredients() {
    this.ingredientNameDTOList.ingredientNameDTOList = this.ingredientNameDTOS;
    this.recipeService.findAllByIngredients(this.ingredientNameDTOList,0, 10, "name").subscribe(data => {
      this.recipes = data;
      for (let i = 0; i < this.recipes.length; i++) {
        this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
          this.recipes[i].authorName = data.login;
        })
      }
    });
    this.recipeDiv = true;
  }
}
