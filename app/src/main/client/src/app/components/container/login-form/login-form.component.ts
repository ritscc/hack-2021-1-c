import { Component, OnInit, Output, EventEmitter } from '@angular/core';

import { AccessTokenModel } from 'src/app/model/user-model';
import { AuthService } from 'src/app/shared/services/auth.service';
import { AlertService } from 'src/app/shared/services/alert.service';
import { LoginRequest } from 'src/app/request/login-request';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent implements OnInit {
  @Output() loginTransit: EventEmitter<any> = new EventEmitter<any>();

  hide = true;

  constructor(
    private authService: AuthService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {}

  handleLogin(requestBody: LoginRequest) {
    this.authService.login(requestBody).subscribe(
      (resp) => {
        this.authService.setCredentials(resp.body as AccessTokenModel);
        this.alertService.openSnackBar('ログインに成功しました', 'SUCCESS');
        this.loginTransit.emit();
      },
      (error) => {
        this.authService.logout();
        this.alertService.openSnackBar(error, 'ERROR');
      }
    );
  }
}
