import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { AlertService } from 'src/app/shared/services/alert.service';
import { UserModel } from 'src/app/model/user-model';

@Component({
  selector: 'app-user-edit-card',
  templateUrl: './user-edit-card.component.html',
  styleUrls: ['./user-edit-card.component.css'],
})
export class UserEditCardComponent implements OnInit {
  @Input() user: UserModel = {} as UserModel;

  // 表示するか
  @Input() enableName: boolean = false;
  @Input() enableEmail: boolean = false;
  @Input() enablePassword: boolean = false;
  @Input() enableCurrentPassword: boolean = false;
  @Input() enableNewPassword: boolean = false;
  @Input() enableRoleId: boolean = false;

  // 編集を許可するか
  @Input() allowNameInput: boolean = false;
  @Input() allowEmailInput: boolean = false;
  @Input() allowPasswordInput: boolean = false;
  @Input() allowCurrentPasswordInput: boolean = false;
  @Input() allowNewPasswordInput: boolean = false;
  @Input() allowRoleIdInput: boolean = false;

  @Output() submitUser: EventEmitter<UserModel> = new EventEmitter<UserModel>();
  @Output() goBackTransit: EventEmitter<any> = new EventEmitter<any>();

  hidePassword = true;
  hideCurrentPassword = true;
  hideNewPassword = true;

  constructor(private alertService: AlertService) {}

  ngOnInit(): void {}

  onClickDisabledColumn(disabled: boolean): void {
    if (disabled) {
      this.alertService.openSnackBar('この項目は編集できません', 'WARN');
    }
  }

  onGoBack(): void {
    this.goBackTransit.emit();
  }

  onSubmit(): void {
    this.submitUser.emit(this.user);
  }
}
