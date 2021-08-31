import { Component, OnInit, Inject } from '@angular/core';
import {
  MAT_SNACK_BAR_DATA,
  MatSnackBarRef,
} from '@angular/material/snack-bar';

import { AlertModel } from 'src/app/model/alert-model';

interface SnackBarData {
  message: string;
  level: 'SUCCESS' | 'INFO' | 'ERROR' | 'WARN';
}

@Component({
  selector: 'app-snack-bar',
  templateUrl: './snack-bar.component.html',
  styleUrls: ['./snack-bar.component.css'],
})
export class SnackBarComponent implements OnInit {
  constructor(
    public snackBarRef: MatSnackBarRef<SnackBarComponent>,
    @Inject(MAT_SNACK_BAR_DATA) public data: AlertModel
  ) {}

  ngOnInit(): void {}

  dismiss() {
    this.snackBarRef.dismiss();
  }
}
