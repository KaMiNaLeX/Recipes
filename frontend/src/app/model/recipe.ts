export class Recipe {
  id: string;
  name: string;
  cookingDifficulty: string;
  cookingTime: number;
  positiveVotes: number;
  negativeVotes: number;
  author_id: string;
  lastModified: Date = new Date();
  imgSource: string;
}
