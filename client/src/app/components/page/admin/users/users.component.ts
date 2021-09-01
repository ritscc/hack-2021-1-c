import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserModel } from 'src/app/model/user-model';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}

  handleUserNewTransit(): void {
    this.router.navigate(['/admin', 'users', 'new']);
  }

  handleUserEditTransit(user: UserModel): void {
    this.router.navigate(['/admin', 'users', user.id, 'edit']);
  }
}

