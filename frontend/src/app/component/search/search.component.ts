import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RecipeService} from "../../service/recipe.service";
import {CategoryService} from "../../service/category.service";
import {IngredientService} from "../../service/ingredient.service";
import {Recipe} from "../../model/recipe";
import {User} from "../../model/user";
import {Ingredient} from "../../model/ingredient";
import {IngredientRecipeDTO} from "../../model/createRecipe/ingredient-recipe-dto";
import {IngredientNameDTO} from "../../model/findByIngredients/ingredient-name-dto";
import {IngredientNameListDTO} from "../../model/findByIngredients/ingredient-name-list-dto";
import {RecipeDataDTO} from "../../model/findByData/recipe-data-dto";
import {CookingDifficultyDTO} from "../../model/findByData/cooking-difficulty-dto";
import {FavoriteService} from "../../service/favorite.service";
import {SharedService} from "../../service/shared.service";
import {PageEvent} from "@angular/material/paginator";
import {UtilsService} from "../../service/utils.service";
import {FormControl} from "@angular/forms";
import {MatAutocomplete, MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {Fruit} from "../../model/fruit";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  ru: boolean;
  recipeDiv = false;
  recipes: Recipe[] = [];
  recipe: Recipe = new Recipe();
  author: User = new User();
  allIngredients: Ingredient[] = [];
  ingredient: IngredientRecipeDTO = new IngredientRecipeDTO();
  keys = [];
  ingredientNameDTOList: IngredientNameListDTO = new IngredientNameListDTO();
  ingredientNameDTOS: IngredientNameDTO[] = [];
  recipeData: RecipeDataDTO = new RecipeDataDTO();
  authenticated = false;
  // MatPaginator Inputs
  length: number;
  pageSize = 8;
  pageSizeOptions: number[] = [8, 32, 64];
  // MatPaginator Output
  pageEvent: PageEvent;
  value = 'Clear me';
  //chips
  selectable = true;
  removable = true;
  fruitCtrl = new FormControl();
  filteredIngredients: Ingredient[];
  fruits: Fruit[] = [];
  chkArr: boolean[] = [false, false, false];
  chkArrValue: string[] = [null, null, null];
  //
  searchArr: string[] = ['recipeName', 'authorName', 'byIngredients', 'recipeData'];
  currentsSearch: string;

  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  constructor(private route: ActivatedRoute, private router: Router, private recipeService: RecipeService,
              private categoryService: CategoryService, private ingredientService: IngredientService,
              private favoriteService: FavoriteService, private ss: SharedService, private utilsService: UtilsService) {
    if (localStorage.getItem('token') != undefined) {
      this.authenticated = true;
    }
  }

  ngOnInit() {
    this.ingredientService.findAllIngredients().subscribe((data: Ingredient[]) => {
      this.allIngredients = data;
      this.filteredIngredients = data;
    });
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => this.ru = item);
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.toLowerCase();
    this.filteredIngredients = this.allIngredients.filter(fruit => fruit.name.toLowerCase().indexOf(filterValue) === 0);
  }

  applyFilterRu(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.toLowerCase();
    this.filteredIngredients = this.allIngredients.filter(fruit => fruit.nameRu.toLowerCase().indexOf(filterValue) === 0);
  }

  remove(ingredientName: string): void {
    for (let i = 0; i < this.fruits.length; i++) {
      let fruit = new Fruit();
      fruit = this.fruits[i];
      if (fruit.name == ingredientName) {
        this.fruits.splice(i, 1);
      }
    }
    for (let i = 0; i < this.ingredientNameDTOS.length; i++) {
      if (this.ingredientNameDTOS[i].name == ingredientName) {
        this.ingredientNameDTOS.splice(i, 1);
      }
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.fruitInput.nativeElement.value = '';
    this.fruitCtrl.setValue(null);

    let fruit = new Fruit();
    let ingredientNameDTO = new IngredientNameDTO();
    this.ingredientService.getByName(event.option.viewValue).subscribe(data => {
      let ingredient = data;
      ingredientNameDTO.name = ingredient.name;
      ingredientNameDTO.nameRu = ingredient.nameRu;
      fruit.name = ingredient.name;
      fruit.nameRu = ingredient.nameRu;
    });

    this.ingredientNameDTOS.push(ingredientNameDTO);
    this.fruits.push(fruit);
  }

  searchByName(name: string) {
    this.recipeService.getByName(name, 0, this.pageSize, "name").subscribe(data => {
      this.recipes = data;
      for (let i = 0; i < this.recipes.length; i++) {
        this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
          this.recipes[i].authorName = data.login;
        })
      }
    });
    this.recipeService.getCountAllRecipesByName(name).subscribe(data => {
      this.length = data;
    });
    this.currentsSearch = this.searchArr[0];
    this.recipeDiv = true;
  }

  searchByAuthorName(name: string) {
    this.recipeService.getByAuthorName(name, 0, this.pageSize, "name").subscribe(data => {
      this.recipes = data;
      for (let i = 0; i < this.recipes.length; i++) {
        this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
          this.recipes[i].authorName = data.login;
        })
      }
    });
    this.recipeService.getCountAllRecipesByAuthorName(name).subscribe(data => {
      this.length = data;
    });
    this.currentsSearch = this.searchArr[1];
    this.recipeDiv = true;
  }

  view(id: string) {
    sessionStorage.setItem('recipe', id);
    this.router.navigate(['recipe-view']);
  }

  addToFavorite(id: string) {
    this.favoriteService.addToFavorite(id, this.authenticated);
  }

  chkAllChange1(event: MatCheckboxChange, value: string) {
    this.chkArr[0] = event.checked;
    this.chkArrValue[0] = value;
  }

  chkAllChange2(event: MatCheckboxChange, value: string) {
    this.chkArr[1] = event.checked;
    this.chkArrValue[1] = value;
  }

  chkAllChange3(event: MatCheckboxChange, value: string) {
    this.chkArr[2] = event.checked;
    this.chkArrValue[2] = value;
  }

  searchByData(time: number) {
    let easy = this.chkArr[0];
    let medium = this.chkArr[1];
    let hard = this.chkArr[2];
    let cookingDifficultyDTOList = new Array<CookingDifficultyDTO>();
    if (easy != false) {
      let dif = new CookingDifficultyDTO();
      dif.cookingDifficulty = this.chkArrValue[0];
      cookingDifficultyDTOList.push(dif);
    }
    if (medium != false) {
      let dif = new CookingDifficultyDTO();
      dif.cookingDifficulty = this.chkArrValue[1];
      cookingDifficultyDTOList.push(dif);

    }
    if (hard != false) {
      let dif = new CookingDifficultyDTO();
      dif.cookingDifficulty = this.chkArrValue[2];
      cookingDifficultyDTOList.push(dif);
    }

    if (time == undefined) {
      time = 0;
    }

    this.recipeData.cookingTime = +time;
    this.recipeData.category = "";
    this.recipeData.includeMeat = true;
    this.recipeData.cookingDifficultyDTOList = cookingDifficultyDTOList;

    this.recipeService.findAllByData(this.recipeData, 0, this.pageSize, "name").subscribe(data => {
      this.recipes = data;
      for (let i = 0; i < this.recipes.length; i++) {
        this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
          this.recipes[i].authorName = data.login;
        })
      }
      this.recipeService.getCountAllRecipesByData().subscribe(data => {
        this.length = data;
      })
    });
    this.currentsSearch = this.searchArr[3];
    this.recipeDiv = true;
  }

  searchByIngredients() {
    this.ingredientNameDTOList.ingredientNameDTOList = this.ingredientNameDTOS;
    this.recipeService.findAllByIngredients(this.ingredientNameDTOList, 0, this.pageSize, "name").subscribe(data => {
      this.recipes = data;
      for (let i = 0; i < this.recipes.length; i++) {
        this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
          this.recipes[i].authorName = data.login;
        })
      }
      this.recipeService.getCountAllRecipesByIngredient().subscribe(data => {
        this.length = data;
      })
    });
    this.currentsSearch = this.searchArr[2];
    this.recipeDiv = true;
  }

  getServerData(event?: PageEvent) {
    if (this.currentsSearch == this.searchArr[0]) {
      let name;
      if (this.ru == true) {
        name = <HTMLInputElement>document.getElementById('recipeNameRu');
      } else {
        name = <HTMLInputElement>document.getElementById('recipeName');
      }
      this.recipeService.getByName(name.value, event.pageIndex, event.pageSize, "name").subscribe(data => {
        this.recipes = data;
        for (let i = 0; i < this.recipes.length; i++) {
          this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
            this.recipes[i].authorName = data.login;
          })
        }
      });
    } else if (this.currentsSearch == this.searchArr[1]) {
      let authorName = <HTMLInputElement>document.getElementById('authorName');
      this.recipeService.getByAuthorName(authorName.value, event.pageIndex, event.pageSize, "name").subscribe(
        response => {
          this.recipes = response;
          if (this.recipes != null) {
            for (let i = 0; i < this.recipes.length; i++) {
              this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
                this.recipes[i].authorName = data.login;
              })
            }
          }
        }
      );
    } else if (this.currentsSearch == this.searchArr[2]) {
      this.recipeService.findAllByIngredients(this.ingredientNameDTOList, event.pageIndex, event.pageSize, "name").subscribe(data => {
        this.recipes = data;
        for (let i = 0; i < this.recipes.length; i++) {
          this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
            this.recipes[i].authorName = data.login;
          })
        }
      });
    } else if (this.currentsSearch == this.searchArr[3]) {
      this.recipeService.findAllByData(this.recipeData, event.pageIndex, event.pageSize, "name").subscribe(data => {
        this.recipes = data;
        for (let i = 0; i < this.recipes.length; i++) {
          this.recipeService.getAuthorName(this.recipes[i].authorId).subscribe((data: User) => {
            this.recipes[i].authorName = data.login;
          })
        }
      });
    }
    return undefined;
  }
}
