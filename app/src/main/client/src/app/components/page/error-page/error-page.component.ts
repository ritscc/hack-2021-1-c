import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ErrorMessageService } from 'src/app/shared/services/error-message.service';

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css'],
})
export class ErrorPageComponent implements OnInit {
  statusCode: number = this.route.snapshot.queryParams.status_code || 500;
  errorMessage = '';
  constructor(
    private route: ActivatedRoute,
    private errorMessageService: ErrorMessageService
  ) {}

  ngOnInit(): void {
    this.errorMessage = this.errorMessageService.getErrorMessage(
      this.statusCode
    );
    this.errorMessage = this.errorMessageService.getErrorMessage(this.statusCode);
  }
}
