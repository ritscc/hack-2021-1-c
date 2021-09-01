import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from 'src/app/shared/services/user.service';
import { AlertService } from 'src/app/shared/services/alert.service';
import { UserModel } from 'src/app/model/user-model';
import { LoginUserPasswordUpdateRequest } from 'src/app/request/user-request';

@Component({
  selector: 'app-mypage-password-edit-form',
  templateUrl: './mypage-password-edit-form.component.html',
  styleUrls: ['./mypage-password-edit-form.component.css'],
})
export class MypagePasswordEditFormComponent implements OnInit {
  user!: UserModel;

  constructor(
    private userService: UserService,
    private alertService: AlertService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // ログインユーザを取得
    this.userService.getLoginUser().subscribe(
      (user: UserModel) => {
        this.user = user;
      },
      (error) => {
        this.alertService.openSnackBar(error, 'ERROR');
      }
    );
  }

  handleGoBack(): void {
    this.router.navigate(['/dashboard']);
  }

  handleSubmitUser(user: UserModel): void {
    // ログインユーザのパスワード更新リクエストを作成
    const requestBody: LoginUserPasswordUpdateRequest = {
      currentPassword: user.currentPassword,
      newPassword: user.newPassword,
    };

    this.alertService.confirmDialog(
      '更新確認',
      'この内容でパスワードを更新しますか？',
      (result: boolean): void => {
        if (result) {
          // リクエスト送信
          this.userService.updateLoginUserPassword(requestBody).subscribe(
            () => {
              this.alertService.openSnackBar(
                'パスワードを更新しました',
                'SUCCESS'
              );
              this.alertService.openSnackBar(
                'パスワードを更新しました',
                'SUCCESS'
              );
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
