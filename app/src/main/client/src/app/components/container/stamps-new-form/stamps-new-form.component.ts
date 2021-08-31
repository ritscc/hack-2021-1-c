import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AlertService } from 'src/app/shared/services/alert.service';
import { StampService } from 'src/app/shared/services/stamp.service';
import { StampModel } from 'src/app/model/stamp-model';
import { StampCreateRequest } from 'src/app/request/stamp-request';

@Component({
  selector: 'app-stamps-new-form',
  templateUrl: './stamps-new-form.component.html',
  styleUrls: ['./stamps-new-form.component.css'],
})
export class StampsNewFormComponent implements OnInit {
  constructor(
    private alertService: AlertService,
    private stampService: StampService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  handleGoBack(): void {
    this.router.navigate(['/stamps']);
  }

  handleSubmitStamp(stamp: StampModel): void {
    // スタンプ作成リクエスト
    const requestBody: StampCreateRequest = {
      title: stamp.title,
      description: stamp.description,
      // FIXME: 添付ファイルリストを入れる
      attachments: [],
    };

    // リクエスト送信
    this.stampService.createUser(requestBody).subscribe(
      () => {
        this.stampService.fetchStamps();
        this.handleGoBack();
        this.alertService.openSnackBar('スタンプを新規作成しました', 'SUCCESS');
      },
      (error) => {
        this.alertService.openSnackBar(error, 'ERROR');
      }
    );
  }
}
