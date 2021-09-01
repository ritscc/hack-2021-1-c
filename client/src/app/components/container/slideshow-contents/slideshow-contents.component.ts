import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

import { StampModel, StampAttachmentModel } from 'src/app/model/stamp-model';
import { AlertService } from 'src/app/shared/services/alert.service';
import { StampService } from 'src/app/shared/services/stamp.service';

@Component({
  selector: 'app-slideshow-contents',
  templateUrl: './slideshow-contents.component.html',
  styleUrls: ['./slideshow-contents.component.css'],
})
export class SlideshowContentsComponent implements OnInit {
  stamps: StampModel[] = [];
  attachments: StampAttachmentModel[] = [];
  images: SafeUrl[] = [];
  slideIndex: number = 0;

  constructor(
    private alertService: AlertService,
    private stampService: StampService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    // スタンプ一覧を取得
    this.stampService.getStamps().subscribe(
      (stamps) => {
        // 添付ファイル一覧を取得
        stamps.forEach((stamp) => {
          stamp.attachments.forEach((attachment) => {
            attachment.stampId = stamp.id;
            this.attachments.push(attachment);
            this.downloadAttachment(attachment);
          });
        });
        this.stamps = stamps;
      },
      (error) => {
        this.alertService.openSnackBar(error, 'ERROR');
      }
    );
  }

  downloadAttachment(attachment: StampAttachmentModel): void {
    this.stampService.downloadAttachment(attachment.id).subscribe(
      (attachmentBlob) => {
        let reader = new FileReader();
        reader.readAsDataURL(attachmentBlob);
        reader.onloadend = () => {
          let src = this.sanitizer.bypassSecurityTrustUrl(reader.result as string);
          this.images.push(src);
        };
      },
      (error) => {
        this.alertService.openSnackBar(error, 'ERROR');
      }
    );
  }

  getSlideTitle(index: number): string {
    if (this.attachments[index] === undefined) {
      return '';
    }

    const stampId = this.attachments[index].stampId;
    // const stamp = this.stampService.selectById(this.attachments[index].stampId);
    const stamp = this.stamps.find((stamp) => stamp.id == stampId);
    if (stamp === undefined) {
      return '';
    } else {
      return stamp.title;
    }
  }

  getImageSrc(index: number): SafeUrl {
    return this.images[index];
  }

  onPreviousClick(): void {
    this.slideIndex--;
    if (this.slideIndex < 0) {
      this.slideIndex = this.images.length - 1;
    }
  }

  onNextClick(): void {
    this.slideIndex++;
    this.slideIndex %= this.images.length;
  }
}
