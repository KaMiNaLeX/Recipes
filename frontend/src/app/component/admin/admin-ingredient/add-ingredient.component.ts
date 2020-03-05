import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {IngredientService} from "../../../service/ingredient.service";
import {Ingredient} from "../../../model/ingredient";
import {SharedService} from "../../../service/shared.service";
import {MatDialog} from "@angular/material/dialog";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {AddIngredientDialogComponent} from "./add-ingredient-dialog/add-ingredient-dialog.component";
import {EditIngredientDialogComponent} from "./edit-ingredient-dialog/edit-ingredient-dialog.component";
import {DeleteIngredientDialogComponent} from "./delete-ingredient-dialog/delete-ingredient-dialog.component";

@Component({
  selector: 'app-add-ingredient',
  templateUrl: './add-ingredient.component.html',
  styleUrls: ['./add-ingredient.component.css']
})
export class AddIngredientComponent implements OnInit {
  ru: boolean;
  ingredients: Ingredient[] = [];
  ingredient: Ingredient = new Ingredient();
  displayedColumns: string[] = ['name', 'description', 'calories', 'type', 'actions'];
  displayedColumnsRu: string[] = ['nameRu', 'descriptionRu', 'calories', 'typeRu', 'actions'];
  dataSource: any;

  constructor(private router: Router, private ingredientService: IngredientService, private ss: SharedService,
              private dialog: MatDialog) {
  }

  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;

  ngOnInit() {
    this.ingredientService.findAll(0, 100, "name").subscribe(data => {
      this.ingredients = data;
      this.dataSource = new MatTableDataSource<Ingredient>(this.ingredients);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => {
        this.ru = item;
        this.dataSource = new MatTableDataSource<Ingredient>(this.ingredients);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openAddDialog() {
    const dialogRef = this.dialog.open(AddIngredientDialogComponent, {
      maxWidth: '30%',
      maxHeight: '50%',
      data: {}
    });
  }

  openEditDialog(id: string) {
    const dialogRef = this.dialog.open(EditIngredientDialogComponent, {
      maxWidth: '30%',
      maxHeight: '50%',
      data: {id: id}
    });
  }

  openDeleteDialog(id: string) {
    const dialogRef = this.dialog.open(DeleteIngredientDialogComponent, {
      maxWidth: '30%',
      maxHeight: '50%',
      data: {id: id}
    });

    dialogRef.afterClosed().subscribe(result => {

    })
  }
}
