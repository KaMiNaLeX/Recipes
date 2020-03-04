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
  firstDiv = true;

  displayedColumns: string[] = ['imgSource', 'name', 'description', 'tag', 'actions'];
  displayedColumnsRu: string[] = ['imgSource', 'nameRu', 'descriptionRu', 'tagRu', 'actions'];
  dataSource: any;

  constructor(private router: Router, private categoryService: CategoryService, private ss: SharedService,
              private dialog: MatDialog) {

  }

  @ViewChild(MatPaginator) set matPaginator(paginator: MatPaginator) {
    this.dataSource.paginator = paginator;
  }

  @ViewChild(MatSort) set matSort(sort: MatSort) {
    this.dataSource.sort = sort;
  }

  ngOnInit() {
    this.categoryService.findAll(0, 10, "name").subscribe(data => {
      this.allCategories = data;
      this.dataSource = new MatTableDataSource<Category>(this.allCategories);
    });
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => this.ru = item);
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
    window.alert("Category is deleted!");
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openAddDialog() {
    const dialogRef = this.dialog.open(AddCategoryDialogComponent, {
      maxWidth: '50%',
      maxHeight: '50%',
      data: {}
    });
  }

  openEditDialog(id: number) {
    const dialogRef = this.dialog.open(EditCategoryDialogComponent, {
      maxWidth: '50%',
      maxHeight: '50%',
      data: {id: id}
    });
  }

  openDeleteDialog(id: number) {
    const dialogRef = this.dialog.open(DeleteCategoryDialogComponent, {
      maxWidth: '50%',
      maxHeight: '50%',
      data: {id: id}
    });

    dialogRef.afterClosed().subscribe(result => {

    })
  }
}
