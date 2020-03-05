import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from "../../../model/user";
import {UserService} from "../../../service/user.service";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {AddUserDialogComponent} from "./add-user-dialog/add-user-dialog.component";
import {EditUserDialogComponent} from "./edit-user-dialog/edit-user-dialog.component";
import {DeleteUserDialogComponent} from "./delete-user-dialog/delete-user-dialog.component";


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[];
  user: User = new User();
  displayedColumns: string[] = ['login', 'first name', 'last name', 'email', 'actions'];
  dataSource: any;

  constructor(private userService: UserService, private router: Router, private dialog: MatDialog) {
  }

  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;

  ngOnInit() {
    this.userService.findAll(0, 100, "login").subscribe(data => {
      this.users = data;
      this.dataSource = new MatTableDataSource<User>(this.users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openAddDialog() {
    const dialogRef = this.dialog.open(AddUserDialogComponent, {
      maxWidth: '30%',
      maxHeight: '50%',
      data: {}
    });
  }

  openEditDialog(id: string) {
    const dialogRef = this.dialog.open(EditUserDialogComponent, {
      maxWidth: '30%',
      maxHeight: '50%',
      data: {id: id}
    });
  }

  openDeleteDialog(id: string) {
    const dialogRef = this.dialog.open(DeleteUserDialogComponent, {
      maxWidth: '30%',
      maxHeight: '50%',
      data: {id: id}
    });

    dialogRef.afterClosed().subscribe(result => {

    })
  }
}
