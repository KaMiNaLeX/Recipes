import {CategoryRecipeDTO} from "./category-recipe-dto";
import {IngredientRecipeDTO} from "./ingredient-recipe-dto";
import {CookingStepRecipeDTO} from "./cooking-step-recipe-dto";

export class CreateRecipeDTO {
  id: string;
  name: string;
  nameRu: string;
  cookingDifficulty: string;
  cookingDifficultyRu: string;
  cookingTime: number;
  positiveVotes: number;
  negativeVotes: number;
  lastModified: Date;
  authorId: string;
  imgSource: string;
  authorName:string;
  categoryRecipeDTOList: Array<CategoryRecipeDTO>;
  ingredientRecipeDTOList: Array<IngredientRecipeDTO>;
  cookingStepRecipeDTOList: Array<CookingStepRecipeDTO>;
  inFavorite: boolean = false;
  positiveVote: boolean = false;
  negativeVote: boolean = false;
}
