import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from 'src/app/shared/services/user.service';
import { AlertService } from 'src/app/shared/services/alert.service';
import { UserModel } from 'src/app/model/user-model';
import { UserUpdateRequest } from 'src/app/request/user-request';

@Component({
  selector: 'app-users-edit-form',
  templateUrl: './users-edit-form.component.html',
  styleUrls: ['./users-edit-form.component.css'],
})
export class UsersEditFormComponent implements OnInit {
  user!: UserModel;
  hide = true;

  constructor(
    private userService: UserService,
    private alertService: AlertService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // ユーザID
    const userId = Number(this.route.snapshot.paramMap.get('userId'));

    // 編集対象ユーザを取得
    const user: UserModel | undefined = this.userService.selectById(userId);
    if (user === undefined) {
      this.router.navigate(['/admin', 'users']);
      this.alertService.openSnackBar('ユーザが見つかりません', 'ERROR');
    } else {
      this.user = user;
    }
  }

  handleGoBack(): void {
    this.router.navigate(['/admin', 'users']);
  }

  handleSubmitUser(user: UserModel): void {
    // ユーザ更新リクエストを作成
    const requestBody: UserUpdateRequest = {
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      roleId: user.roleId,
    };

    this.alertService.confirmDialog(
      '更新確認',
      'この内容でユーザ情報を更新しますか？',
      (result: boolean): void => {
        if (result) {
          // リクエスト送信
          this.userService.updateUser(user.id, requestBody).subscribe(
            () => {
              this.handleGoBack();
              this.alertService.openSnackBar('ユーザを更新しました', 'SUCCESS');
            },
            (error) => {
              this.alertService.openSnackBar(error, 'ERROR');
            }
          );
        }
      }
    );
  }
}
