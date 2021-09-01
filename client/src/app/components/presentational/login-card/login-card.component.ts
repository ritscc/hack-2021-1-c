import { Component, OnInit, Output, EventEmitter } from '@angular/core';

import { LoginRequest } from 'src/app/request/login-request';

@Component({
  selector: 'app-login-card',
  templateUrl: './login-card.component.html',
  styleUrls: ['./login-card.component.css'],
})
export class LoginCardComponent implements OnInit {
  @Output() submitLogin: EventEmitter<LoginRequest> =
    new EventEmitter<LoginRequest>();

  loginRequest!: LoginRequest;
  hide = true;

  constructor() {}

  ngOnInit(): void {
    this.loginRequest = {} as LoginRequest;
  }

  onSubmit(): void {
    this.submitLogin.emit(this.loginRequest);
  }
}
