export class Recipe {
  id: string;
  name: string;
  cookingDifficulty: string;
  cookingTime: number;
  positiveVotes: number;
  negativeVotes: number;
  authorId: string;
  authorName:string;
  lastModified: Date = new Date();
  imgSource: string;
}
