import { Component, OnInit, EventEmitter, Output } from '@angular/core';

import { StampModel } from 'src/app/model/stamp-model';
import { StampService } from 'src/app/shared/services/stamp.service';
import { AlertService } from 'src/app/shared/services/alert.service';

@Component({
  selector: 'app-stamps-contents',
  templateUrl: './stamps-contents.component.html',
  styleUrls: ['./stamps-contents.component.css'],
})
export class StampsContentsComponent implements OnInit {
  @Output() stampNewTransit: EventEmitter<any> = new EventEmitter();

  stamps!: StampModel[];

  constructor(
    private stampService: StampService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    // スタンプ一覧を取得
    this.stampService.getStamps().subscribe(
      (stamps: StampModel[]) => {
        this.stamps = stamps;
      },
      (error) => {
        this.alertService.openSnackBar(error, 'ERROR');
      }
    );
  }

  onClickCreateButton(): void {
    this.stampNewTransit.emit();
  }

  handleDeleteStamp(stamp: StampModel): void {
    this.alertService.confirmDialog(
      '削除確認',
      'この動作は取り消せませんが、本当に削除しますか？',
      (result: boolean): void => {
        if (result) this.deleteStamp(stamp);
      }
    );
  }

  deleteStamp(stamp: StampModel): void {
    this.stampService.deleteUser(stamp.id).subscribe(
      () => {
        this.stampService.fetchStamps();
        this.alertService.openSnackBar('スタンプを削除しました', 'SUCCESS');
      },
      (error) => {
        this.alertService.openSnackBar(error, 'ERROR');
      }
    );
  }
}
