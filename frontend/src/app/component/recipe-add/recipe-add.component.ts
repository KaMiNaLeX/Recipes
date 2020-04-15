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
import {UnitRu} from "../../model/unit-ru.enum";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {Fruit} from "../../model/fruit";
import {MatTableDataSource} from "@angular/material/table";
import {TranslateResponse} from "../../model/translate-response";

@Component({
  selector: 'app-recipe-add',
  templateUrl: './recipe-add.component.html',
  styleUrls: ['./recipe-add.component.css']
})
export class RecipeAddComponent implements OnInit {
  createRecipeDTO: CreateRecipeDTO;
  ingredients: IngredientRecipeDTO[] = [];
  ingredient: IngredientRecipeDTO = new IngredientRecipeDTO();
  categories: CategoryRecipeDTO[];
  category: CategoryRecipeDTO = new CategoryRecipeDTO();
  checkedArray: CategoryRecipeDTO[] = [];
  cookingStep: CookingStepRecipeDTO = new CookingStepRecipeDTO();
  cookingSteps: CookingStepRecipeDTO[] = [];
  unit = [];
  unRu = [];
  un = [];
  allIngredients: Ingredient[];
  recipe: Recipe;
  selectedFile: File[] = [];
  imgURL: any = null;
  selectedFile2: File = null;
  imgURL2: any;
  ru: boolean;
  ///
  isEditable = true;
  removable = true;
  fruits: Fruit[] = [];
  ///
  dataSource: any;
  displayedColumns: string[] = ['photo', 'description', 'actions'];

  constructor(private route: ActivatedRoute, private router: Router, private recipeService: RecipeService,
              private categoryService: CategoryService, private ingredientService: IngredientService, private ss: SharedService,
              private utilsService: UtilsService) {
    this.createRecipeDTO = new CreateRecipeDTO();

  }

  ngOnInit() {
    let u = Unit;
    this.un = Object.values(u);
    let ur = UnitRu;
    this.unRu = Object.values(ur);

    this.categoryService.findAllCategoriesDTO().subscribe(data => {
      this.categories = data;
    });
    this.ingredientService.findAllIngredients().subscribe(data => this.allIngredients = data);
    this.ru = (localStorage.getItem('lang') == 'ru');
    if (this.ru != true) {
      let u = Unit;
      this.unit = Object.values(u);
    } else {
      let u = UnitRu;
      this.unit = Object.values(u);
    }
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

  checkRecipe() {
    if ((this.createRecipeDTO.name != null && this.createRecipeDTO.name != "") || (this.createRecipeDTO.nameRu != null
      && this.createRecipeDTO.nameRu != "")) {
      if (this.ru != true) {
        this.recipeService.getByNameAndAuthor(this.createRecipeDTO.name, localStorage.getItem('id')).subscribe(data => {
          this.recipe = data;
          if (this.recipe != null) {
            this.createRecipeDTO.name = null;
            this.utilsService.alert("have recipe with this name");
          }
        })
      } else {
        this.recipeService.getByNameAndAuthor(this.createRecipeDTO.nameRu, localStorage.getItem('id')).subscribe(data => {
          this.recipe = data;
          if (this.recipe != null) {
            this.createRecipeDTO.nameRu = null;
            this.utilsService.alert("have recipe with this name");
          }
        })
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
    this.fruits.push(fruit);
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
    step.active = true;
    if (this.imgURL == null) {
      step.imgSource = "http://localhost:4200/getFile/noImage.png";
    } else {
      step.imgSource = this.imgURL;
    }
    if (this.ru != true) {
      this.utilsService.translate('en-ru', this.createRecipeDTO.name).subscribe((data: TranslateResponse) => {
        this.createRecipeDTO.nameRu = data.text[0];
      });
      this.utilsService.translate('en-ru', description).subscribe((data: TranslateResponse) => {
        step.descriptionRu = data.text[0];
        step.description = description;
      });
    } else {
      this.utilsService.translate('ru-en', this.createRecipeDTO.nameRu).subscribe((data: TranslateResponse) => {
        this.createRecipeDTO.name = data.text[0];
      });
      this.utilsService.translate('ru-en', description).subscribe((data: TranslateResponse) => {
        step.description = data.text[0];
        step.descriptionRu = description;
      });
    }
    this.cookingSteps.push(step);
    this.cookingStep.description = null;
    this.imgURL = null;
    this.dataSource = new MatTableDataSource<CookingStepRecipeDTO>(this.cookingSteps);
  }

  handleFileInput(event) {
    this.selectedFile.push(event.target.files[0]);
    // Below part is used to display the selected image
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = (event2) => {
      this.imgURL = reader.result;
    }
  }

  handleFileInput2(event) {
    this.selectedFile2 = event.target.files[0];
    // Below part is used to display the selected image
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = (event2) => {
      this.imgURL2 = reader.result;
    }
  }

  onSubmit() {
    this.createRecipeDTO.imgSource = null;
    for (let i = 0; i < this.cookingSteps.length; i++) {
      if (this.cookingSteps[i].imgSource.length > 1000)
        this.cookingSteps[i].imgSource = null;
    }
    this.createRecipeDTO.cookingStepRecipeDTOList = this.cookingSteps;
    this.createRecipeDTO.ingredientRecipeDTOList = this.ingredients;
    this.createRecipeDTO.authorId = localStorage.getItem("id");
    this.createRecipeDTO.categoryRecipeDTOList = this.checkedArray;
    // need to fix
    this.createRecipeDTO.cookingDifficultyRu = "ЛЕГКО";
    if (this.cookingSteps.length != 0 &&
      this.ingredients.length != 0 &&
      this.checkedArray.length != 0 &&
      this.createRecipeDTO.cookingTime != null &&
      this.createRecipeDTO.name != null &&
      this.createRecipeDTO.cookingDifficulty != null) {
      this.recipeService.createRecipe(this.createRecipeDTO).subscribe(data => {
        this.createRecipeDTO = data;
        if (this.selectedFile2 != null) {
          this.recipeService.addPhoto4Recipe(this.createRecipeDTO.id, this.selectedFile2);
        } else {
          this.selectedFile2 = new File([], "null", undefined);
          this.recipeService.addPhoto4Recipe(this.createRecipeDTO.id, this.selectedFile2);
        }
        for (let i = 0; i < this.createRecipeDTO.cookingStepRecipeDTOList.length; i++) {
          if (this.selectedFile[i] != undefined && this.createRecipeDTO.cookingStepRecipeDTOList[i].imgSource == null) {
            this.recipeService.addPhoto4Step(this.createRecipeDTO.cookingStepRecipeDTOList[i].id, this.selectedFile[i]);
          }
        }
      });
      this.router.navigate(['category']);
      this.utilsService.alert("recipe created");
    } else {
      this.utilsService.alert("fill all fields");
    }
  }

  chkChange(event: MatCheckboxChange, category: CategoryRecipeDTO) {
    if (event.checked == true) {
      this.checkedArray.push(category);
    } else {
      for (let i = 0; i < this.checkedArray.length; i++) {
        if (this.checkedArray[i].name == category.name) {
          this.checkedArray.splice(i, 1);
        }
      }
    }
  }

  translateName() {


  }
}


