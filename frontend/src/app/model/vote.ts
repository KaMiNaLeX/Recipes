export class Vote {
  id: string;
  recipeId: string;
  userId: string;
  negativeVote: boolean = false;
  positiveVote: boolean = false;
}
