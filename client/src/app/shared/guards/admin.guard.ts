import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';

import { UserModel } from 'src/app/model/user-model';
import { UserRoleEnum } from 'src/app/enums/user-role-enum';
import { UserService } from 'src/app/shared/services/user.service';
import { AlertService } from 'src/app/shared/services/alert.service';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {
  constructor(
    private userService: UserService,
    private alertService: AlertService
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    return new Promise((resolve) => {
      this.userService.getLoginUser().subscribe(
        (user: UserModel) => {
          if (user.roleId === undefined) {
            // ログインユーザ情報が未取得なので一時的にtrueを返却
            resolve(true);
          } else if (user.roleId === UserRoleEnum.ADMIN) {
            resolve(true);
          } else {
            this.alertService.openSnackBar(
              'このページへのアクセスは許可されていません',
              'WARN'
            );
            resolve(false);
          }
        },
        (error) => {
          this.alertService.openSnackBar(error, 'ERROR');
          resolve(false);
        }
      );
    });
  }
}
