import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { StampModel } from 'src/app/model/stamp-model';

@Component({
  selector: 'app-stamp-new-card',
  templateUrl: './stamp-new-card.component.html',
  styleUrls: ['./stamp-new-card.component.css'],
})
export class StampNewCardComponent implements OnInit {
  @Output() submitStamp: EventEmitter<StampModel> = new EventEmitter<StampModel>();
  @Output() goBackTransit: EventEmitter<any> = new EventEmitter<any>();

  stamp: StampModel = {} as StampModel;

  constructor() {}

  ngOnInit(): void {}

  onGoBack(): void {
    this.goBackTransit.emit();
  }

  onSubmit(): void {
    this.submitStamp.emit(this.stamp);
  }
}
