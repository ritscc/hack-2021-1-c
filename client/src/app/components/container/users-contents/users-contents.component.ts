import { Component, OnInit, EventEmitter, Output } from '@angular/core';

import { UserModel } from 'src/app/model/user-model';
import { UserService } from 'src/app/shared/services/user.service';
import { AlertService } from 'src/app/shared/services/alert.service';

@Component({
  selector: 'app-users-contents',
  templateUrl: './users-contents.component.html',
  styleUrls: ['./users-contents.component.css'],
})
export class UsersContentsComponent implements OnInit {
  @Output() userNewTransit: EventEmitter<any> = new EventEmitter();
  @Output() userEditTransit: EventEmitter<any> = new EventEmitter<any>();

  users!: UserModel[];

  constructor(
    private userService: UserService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    // ユーザ一覧を取得
    this.userService.getUsers().subscribe(
      (users: UserModel[]) => {
        this.users = users;
        this.users = this.userService.sortUsers(this.users);
      },
      (error) => {
        this.alertService.openSnackBar(error, 'ERROR');
      }
    );
  }

  onClickCreateButton(): void {
    this.userNewTransit.emit();
  }

  handleUserEditTransit(user: UserModel): void {
    this.userEditTransit.emit(user);
  }

  handleUserDelete(user: UserModel): void {
    this.alertService.confirmDialog(
      '削除確認',
      'この動作は取り消せませんが、本当に削除しますか？',
      (result: boolean): void => {
        if (result) this.deleteUser(user);
      }
    );
  }

  deleteUser(user: UserModel): void {
    this.userService.deleteUser(user.id).subscribe(
      () => {
        this.userService.fetchUsers();
        this.alertService.openSnackBar('ユーザを削除しました', 'SUCCESS');
      },
      (error) => {
        this.alertService.openSnackBar(error, 'ERROR');
      }
    );
  }
}
