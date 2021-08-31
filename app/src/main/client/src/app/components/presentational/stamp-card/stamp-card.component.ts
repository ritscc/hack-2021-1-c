import { Component, OnInit, Input } from '@angular/core';

import { StampWithUserModel } from 'src/app/model/stamp-model';

@Component({
  selector: 'app-stamp-card',
  templateUrl: './stamp-card.component.html',
  styleUrls: ['./stamp-card.component.css'],
})
export class StampCardComponent implements OnInit {
  @Input() stamp!: StampWithUserModel;

  constructor() {}

  ngOnInit(): void {}
}
