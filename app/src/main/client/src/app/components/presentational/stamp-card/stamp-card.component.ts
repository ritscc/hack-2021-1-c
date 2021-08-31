import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

import { StampModel, StampWithUserModel } from 'src/app/model/stamp-model';
import { StampService } from 'src/app/shared/services/stamp.service';
import { AlertService } from 'src/app/shared/services/alert.service';

@Component({
  selector: 'app-stamp-card',
  templateUrl: './stamp-card.component.html',
  styleUrls: ['./stamp-card.component.css'],
})
export class StampCardComponent implements OnInit {
  @Input() stamp!: StampWithUserModel;

  @Output() downloadAttachment: EventEmitter<StampModel> = new EventEmitter<StampModel>();
  @Output() deleteStamp: EventEmitter<StampModel> = new EventEmitter<StampModel>();

  images: SafeUrl[] = [];

  constructor(
    private stampService: StampService,
    private alertService: AlertService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.stamp.attachments.forEach((attachment) => {
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
    });
  }

  onDownload(): void {
    this.downloadAttachment.emit(this.stamp);
  }

  onDelete(): void {
    this.deleteStamp.emit(this.stamp);
  }
}
