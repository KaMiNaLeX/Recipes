import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {CategoryService} from "../../../service/category.service";
import {Category} from "../../../model/category";
import {SharedService} from "../../../service/shared.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatDialog} from "@angular/material/dialog";
import {AddCategoryDialogComponent} from "./add-category-dialog/add-category-dialog.component";
import {EditCategoryDialogComponent} from "./edit-category-dialog/edit-category-dialog.component";
import {DeleteCategoryDialogComponent} from "./delete-category-dialog/delete-category-dialog.component";

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {
  ru: boolean;
  allCategories: Category[] = [];
  category: Category = new Category();
  displayedColumns: string[] = ['imgSource', 'name', 'description', 'tag', 'actions'];
  displayedColumnsRu: string[] = ['imgSource', 'nameRu', 'descriptionRu', 'tagRu', 'actions'];
  dataSource: any;

  constructor(private router: Router, private categoryService: CategoryService, private ss: SharedService,
              private dialog: MatDialog) {

  }

  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;

  ngOnInit() {
    this.categoryService.findAll(0, 100, "name").subscribe(data => {
      this.allCategories = data;
      this.dataSource = new MatTableDataSource<Category>(this.allCategories);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => {
        this.ru = item;
        this.dataSource = new MatTableDataSource<Category>(this.allCategories);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      });
  }

  delete(id: number) {
    this.categoryService.delete(id)
      .subscribe(
        data => {
          this.categoryService.findAll(0, 10, "name").subscribe(data => {
            this.allCategories = data;
          })
        },
        error => console.log(error));
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openAddDialog() {
    const dialogRef = this.dialog.open(AddCategoryDialogComponent, {
      maxWidth: '30%',
      maxHeight: '50%',
      data: {}
    });
  }

  openEditDialog(id: number) {
    const dialogRef = this.dialog.open(EditCategoryDialogComponent, {
      maxWidth: '30%',
      maxHeight: '50%',
      data: {id: id}
    });
  }

  openDeleteDialog(id: number) {
    const dialogRef = this.dialog.open(DeleteCategoryDialogComponent, {
      maxWidth: '30%',
      maxHeight: '50%',
      data: {id: id}
    });

    dialogRef.afterClosed().subscribe(result => {

    })
  }
}
