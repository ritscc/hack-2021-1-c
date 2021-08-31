import {
  Component,
  OnInit,
  EventEmitter,
  Input,
  Output,
  OnChanges,
  SimpleChanges,
} from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';

import { UserModel } from 'src/app/model/user-model';

@Component({
  selector: 'app-users-table',
  templateUrl: './users-table.component.html',
  styleUrls: ['./users-table.component.css'],
})
export class UsersTableComponent implements OnInit, OnChanges {
  @Input() users!: UserModel[];
  @Output() userEditTransit: EventEmitter<any> = new EventEmitter<any>();
  @Output() userDeleteTransit: EventEmitter<any> = new EventEmitter<any>();

  columns: string[] = ['name', 'email', 'userRole', 'buttons'];
  dataSource!: MatTableDataSource<UserModel>;

  constructor() {}

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource<UserModel>(this.users);
  }

  onEditClick(user: UserModel): void {
    this.userEditTransit.emit(user);
  }

  onDeleteClick(user: UserModel): void {
    this.userDeleteTransit.emit(user);
  }

  ngOnChanges(changes: SimpleChanges) {
    this.users = changes.users.currentValue;
    this.ngOnInit();
  }
}
