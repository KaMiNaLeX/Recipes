export class Recipe {
  id: string;
  name: string;
  nameRu:string;
  cookingDifficulty: string;
  cookingDifficultyRu: string;
  cookingTime: number;
  positiveVotes: number;
  negativeVotes: number;
  authorId: string;
  authorName:string;
  lastModified: Date = new Date();
  imgSource: string;
}
