import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { StampModel, StampWithUserModel } from 'src/app/model/stamp-model';

@Component({
  selector: 'app-stamp-card',
  templateUrl: './stamp-card.component.html',
  styleUrls: ['./stamp-card.component.css'],
})
export class StampCardComponent implements OnInit {
  @Input() stamp!: StampWithUserModel;

  @Output() deleteStamp: EventEmitter<StampModel> = new EventEmitter<StampModel>();

  constructor() {}

  ngOnInit(): void {}

  onDelete(): void {
    this.deleteStamp.emit(this.stamp);
  }
}
