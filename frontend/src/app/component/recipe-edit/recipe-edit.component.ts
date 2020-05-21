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
import {Unit} from "../../model/Enum/unit.enum";
import {SharedService} from "../../service/shared.service";
import {UtilsService} from "../../service/utils.service";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {MatTableDataSource} from "@angular/material/table";
import {Fruit} from "../../model/fruit";
import {UnitRu} from "../../model/Enum/unit-ru.enum";
import {TranslateResponse} from "../../model/translate-response";
import {CookDifficulty} from "../../model/Enum/cook-difficulty.enum";
import {CookDifficultyRu} from "../../model/Enum/cook-difficulty-ru.enum";

@Component({
  selector: 'app-recipe-edit',
  templateUrl: './recipe-edit.component.html',
  styleUrls: ['./recipe-edit.component.css']
})
export class RecipeEditComponent implements OnInit {

  createRecipeDTO: CreateRecipeDTO = new CreateRecipeDTO();
  ingredients: IngredientRecipeDTO[] = [];
  ingredient: IngredientRecipeDTO = new IngredientRecipeDTO();
  categories: CategoryRecipeDTO[];
  category: CategoryRecipeDTO = new CategoryRecipeDTO();
  checkedArray: CategoryRecipeDTO[] = [];
  categoryArray: CategoryRecipeDTO[] = [];
  cookingStep: CookingStepRecipeDTO = new CookingStepRecipeDTO();
  cookingSteps: CookingStepRecipeDTO[] = [];
  unit = [];
  unRu = [];
  un = [];
  diff = [];
  diffRu = [];
  allIngredients: Ingredient[];
  recipe: Recipe;
  primaryName: String;
  primaryNameRu: String;

  selectedFile2: File[] = [];
  imgURL2: any = null;

  selectedFile: File = null;
  imgURL: any;

  ru: boolean;
  ///
  removable = true;
  fruits: Fruit[] = [];
  ///
  dataSource: any;
  displayedColumns: string[] = ['photo', 'description', 'actions'];

  constructor(private route: ActivatedRoute, private router: Router, private recipeService: RecipeService,
              private categoryService: CategoryService, private ingredientService: IngredientService,
              private ss: SharedService, private utilsService: UtilsService) {
  }

  ngOnInit() {
    let u = Unit;
    this.un = Object.values(u);
    let ur = UnitRu;
    this.unRu = Object.values(ur);

    let c = CookDifficulty;
    this.diff = Object.values(c);
    let cr = CookDifficultyRu;
    this.diffRu = Object.values(cr);

    this.recipeService.getById(sessionStorage.getItem('recipe')).subscribe(
      data => {
        this.createRecipeDTO = data;
        this.primaryName = this.createRecipeDTO.name;
        this.primaryNameRu = this.createRecipeDTO.nameRu;
        this.fruits = this.createRecipeDTO.ingredientRecipeDTOList;
        this.cookingSteps = this.createRecipeDTO.cookingStepRecipeDTOList;
        this.categoryArray = this.createRecipeDTO.categoryRecipeDTOList;
        this.ingredients = this.createRecipeDTO.ingredientRecipeDTOList;
        this.dataSource = new MatTableDataSource<CookingStepRecipeDTO>(this.cookingSteps);
        for (let i = 0; i < this.createRecipeDTO.categoryRecipeDTOList.length; i++) {
          this.checkedArray[i] = this.createRecipeDTO.categoryRecipeDTOList[i];
        }
      }
    );
    this.ru = (localStorage.getItem('lang') == 'ru');
    if (this.ru != true) {
      let u = Unit;
      this.unit = Object.values(u);
    } else {
      let u = UnitRu;
      this.unit = Object.values(u);
    }
    this.categoryService.findAllCategoriesDTO().subscribe(data => {
      this.categories = data;
      let category = new CategoryRecipeDTO();
      category.name = null;
      category.nameRu = null;
      for (let i = 0; i < this.categories.length; i++) {
        this.checkedArray.push(category);
      }
    });
    this.ingredientService.findAllIngredients().subscribe(data => this.allIngredients = data);

    this.ss.getEmittedValue()
      .subscribe(item => {
        this.ru = item;
        if (this.ru != true) {
          let u = Unit;
          this.unit = Object.values(u);
        } else {
          let u = UnitRu;
          this.unit = Object.values(u);
        }
      });
  }

  checkRecipeAndTranslate() {
    if (this.ru != true) {
      if (this.createRecipeDTO.name != null && this.createRecipeDTO.name != "" && this.createRecipeDTO.name != this.primaryName) {
        this.recipeService.getByNameAndAuthor(this.createRecipeDTO.name, localStorage.getItem('id')).subscribe(data => {
          this.recipe = data;
          if (this.recipe != null) {
            this.createRecipeDTO.name = null;
            this.utilsService.alert("have recipe with this name");
          } else {
            this.utilsService.translate('en-ru', this.createRecipeDTO.name).subscribe((data: TranslateResponse) => {
              this.createRecipeDTO.nameRu = data.text[0];
            });
          }
        })
      } else {
        if (this.createRecipeDTO.nameRu != null && this.createRecipeDTO.nameRu != "" && this.createRecipeDTO.nameRu != this.primaryNameRu) {
          this.recipeService.getByNameAndAuthor(this.createRecipeDTO.nameRu, localStorage.getItem('id')).subscribe(data => {
            this.recipe = data;
            if (this.recipe != null) {
              this.createRecipeDTO.nameRu = null;
              this.utilsService.alert("have recipe with this name");
            } else {
              this.utilsService.translate('ru-en', this.createRecipeDTO.nameRu).subscribe((data: TranslateResponse) => {
                this.createRecipeDTO.name = data.text[0];
              });
            }
          })
        }
      }
    }
  }

  addIngredient(name: string, amount: number, unit: string) {
    let ingredientRecipeDTO = new IngredientRecipeDTO();
    let fruit = new Fruit();
    this.ingredientService.getByName(name).subscribe(data => {
      ingredientRecipeDTO.nameRu = data.nameRu;
      ingredientRecipeDTO.name = data.name;
      ingredientRecipeDTO.amount = amount;
      fruit.name = data.name;
      fruit.nameRu = data.nameRu;
      fruit.amount = amount;
      if (this.ru != true) {
        ingredientRecipeDTO.unit = unit;
        fruit.unit = unit.toLowerCase();
        let i = this.un.indexOf(unit);
        ingredientRecipeDTO.unitRu = this.unRu[i];
        fruit.unitRu = this.unRu[i].toLowerCase();
      } else {
        ingredientRecipeDTO.unitRu = unit;
        fruit.unitRu = unit.toLowerCase();
        let i = this.unRu.indexOf(unit);
        ingredientRecipeDTO.unit = this.un[i];
        fruit.unit = this.un[i].toLowerCase();
      }
    });
    this.ingredients.push(ingredientRecipeDTO);
  }

  deleteIngredient(ingredientName: string) {
    for (let i = 0; i < this.ingredients.length; i++) {
      let ingredient = new IngredientRecipeDTO();
      ingredient = this.ingredients[i];
      if (ingredient.name == ingredientName) {
        this.ingredients.splice(i, 1);
      }
    }
    for (let i = 0; i < this.fruits.length; i++) {
      let fruit = new Fruit();
      fruit = this.fruits[i];
      if (fruit.name == ingredientName) {
        this.fruits.splice(i, 1);
      }
    }
  }

  deleteStep(stepName: string) {
    for (let i = 0; i < this.cookingSteps.length; i++) {
      let step = new CookingStepRecipeDTO();
      step = this.cookingSteps[i];
      if (step.description == stepName) {
        this.cookingSteps.splice(i, 1);
      }
    }
    this.dataSource = new MatTableDataSource<CookingStepRecipeDTO>(this.cookingSteps);
  }

  addCookingStep(description: string) {
    let step = new CookingStepRecipeDTO();
    if (this.imgURL2 == null) {
      step.imgSource = "http://localhost:4200/getFile/noImage.png";
    } else {
      step.imgSource = this.imgURL2;
    }
    if (this.ru != true) {
      this.utilsService.translate('en-ru', description).subscribe((data: TranslateResponse) => {
        step.descriptionRu = data.text[0];
        step.description = description;
      });
    } else {
      this.utilsService.translate('ru-en', description).subscribe((data: TranslateResponse) => {
        step.description = data.text[0];
        step.descriptionRu = description;
      });
    }
    this.cookingSteps.push(step);
    this.cookingStep.description = null;
    this.imgURL2 = null;
    this.dataSource = new MatTableDataSource<CookingStepRecipeDTO>(this.cookingSteps);
  }

  handleFileInput2(event) {
    this.selectedFile2.push(event.target.files[0]);
    // Below part is used to display the selected image
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = (event2) => {
      this.imgURL2 = reader.result;
    }
  }

  handleFileInput(event) {
    this.selectedFile = event.target.files[0];
    // Below part is used to display the selected image
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = (event2) => {
      this.imgURL = reader.result;
    }
  }

  onSubmit() {
    let y = -1;
    for (let i = 0; i < this.cookingSteps.length; i++) {
      if (this.cookingSteps[i].imgSource.length > 1000) {
        this.cookingSteps[i].imgSource = null;
      }
    }
    if (this.createRecipeDTO.cookingDifficulty != null) {
      let i = this.diff.indexOf(this.createRecipeDTO.cookingDifficulty);
      this.createRecipeDTO.cookingDifficultyRu = this.diffRu[i];
    } else if (this.createRecipeDTO.cookingDifficultyRu != null) {
      let i = this.diffRu.indexOf(this.createRecipeDTO.cookingDifficultyRu);
      this.createRecipeDTO.cookingDifficulty = this.diff[i];
    }
    this.createRecipeDTO.cookingStepRecipeDTOList = this.cookingSteps;
    this.createRecipeDTO.ingredientRecipeDTOList = this.ingredients;
    this.createRecipeDTO.authorId = localStorage.getItem("id");
    this.createRecipeDTO.categoryRecipeDTOList = this.categoryArray;
    if (this.cookingSteps.length != 0 &&
      this.ingredients.length != 0 &&
      this.categoryArray.length != 0 &&
      this.createRecipeDTO.cookingTime != null &&
      this.createRecipeDTO.name != null &&
      this.createRecipeDTO.nameRu != null &&
      this.createRecipeDTO.cookingDifficulty != null) {
      this.recipeService.update(this.createRecipeDTO.id, this.createRecipeDTO).subscribe(data => {
          this.recipeService.getById(this.createRecipeDTO.id).subscribe(data => {
              this.createRecipeDTO = data;
              if (this.selectedFile != null) {
                this.recipeService.addPhoto4Recipe(this.createRecipeDTO.id, this.selectedFile);
              }
              for (let i = 0; i < this.createRecipeDTO.cookingStepRecipeDTOList.length; i++) {
                if (this.createRecipeDTO.cookingStepRecipeDTOList[i].imgSource == null) {
                  this.recipeService.addPhoto4Step(this.createRecipeDTO.cookingStepRecipeDTOList[i].id,
                    this.selectedFile2[y + 1]);

                }
              }
              window.location.reload();
            }
          );
        }
      );
      this.utilsService.alert("recipe is updated");
    } else {
      this.utilsService.alert("fill all fields");
    }
  }

  chkChange(event: MatCheckboxChange, category: CategoryRecipeDTO) {
    if (event.checked == true) {
      this.categoryArray.push(category);
    } else {
      for (let i = 0; i < this.categoryArray.length; i++) {
        if (this.categoryArray[i].name == category.name) {
          this.categoryArray.splice(i, 1);
        }
      }
    }
  }
}
