import { Component, OnInit, EventEmitter, Output } from '@angular/core';

import { StampModel, StampWithUserModel } from 'src/app/model/stamp-model';
import { UserService } from 'src/app/shared/services/user.service';
import { StampService } from 'src/app/shared/services/stamp.service';
import { DownloadBlobService } from 'src/app/shared/services/download-blob.service';
import { AlertService } from 'src/app/shared/services/alert.service';

@Component({
  selector: 'app-stamps-contents',
  templateUrl: './stamps-contents.component.html',
  styleUrls: ['./stamps-contents.component.css'],
})
export class StampsContentsComponent implements OnInit {
  @Output() stampNewTransit: EventEmitter<any> = new EventEmitter();

  stamps: StampWithUserModel[] = [];

  constructor(
    private userService: UserService,
    private stampService: StampService,
    private downloadBlobService: DownloadBlobService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    // スタンプ一覧を取得
    this.userService.getUsers().subscribe((_) => {
      this.stampService.getStamps().subscribe(
        (stamps: StampModel[]) => {
          this.stamps = [];
          stamps.forEach((stamp) => {
            const user = this.userService.selectById(stamp.userId);
            if (user === undefined) {
              return;
            }

            let stampWithUser: StampWithUserModel = stamp as StampWithUserModel;
            stampWithUser.user = user;
            this.stamps.push(stampWithUser);
          });
        },
        (error) => {
          this.alertService.openSnackBar(error, 'ERROR');
        }
      );
    });
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

  handleDownloadAttachment(stamp: StampModel): void {
    stamp.attachments.forEach((attachment) => {
      this.stampService.downloadAttachment(attachment.id).subscribe(
        (attachmentBlob) => {
          this.downloadBlobService.download(attachmentBlob, attachment.name);
        },
        (error) => {
          this.alertService.openSnackBar(error, 'ERROR');
        }
      );
    });
  }

  deleteStamp(stamp: StampModel): void {
    this.stampService.deleteStamp(stamp.id).subscribe(
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
