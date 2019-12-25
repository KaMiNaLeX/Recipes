import {CookingDifficultyDTO} from "./cooking-difficulty-dto";

export class RecipeDataDTO {
  cookingDifficultyDTOList: Array<CookingDifficultyDTO>;
  cookingTime: number;
  includeMeat: boolean;
  category: string;
}
