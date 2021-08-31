import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { StampModel } from 'src/app/model/stamp-model';
import { StampCreateRequest } from 'src/app/request/stamp-request';

@Component({
  selector: 'app-stamp-new-card',
  templateUrl: './stamp-new-card.component.html',
  styleUrls: ['./stamp-new-card.component.css'],
})
export class StampNewCardComponent implements OnInit {
  @Output() submitStamp: EventEmitter<StampCreateRequest> = new EventEmitter<StampCreateRequest>();
  @Output() goBackTransit: EventEmitter<any> = new EventEmitter<any>();

  stamp: StampModel = {} as StampModel;
  requestBody: StampCreateRequest = {} as StampCreateRequest;

  constructor() {}

  ngOnInit(): void {
    this.requestBody.attachments = [];
  }

  onGoBack(): void {
    this.goBackTransit.emit();
  }

  onSubmit(): void {
    this.requestBody.title = this.stamp.title;
    this.requestBody.description = this.stamp.description;
    this.submitStamp.emit(this.requestBody);
  }

  onClickFileSelectButton(event: Event): void {
    (event.target as HTMLInputElement).value = '';
  }

  onSelectFiles(event: Event): void {
    const files: FileList | null = (event.target as HTMLInputElement).files;

    if (files === null) {
      return;
    }

    const reader = new FileReader();
    reader.readAsDataURL(files[0]);
    reader.onload = () => {
      const result = reader.result as string;
      this.requestBody.attachments.push({
        name: files[0].name,
        content: result.slice(result.indexOf(',') + 1),
      });
    };
  }
}
