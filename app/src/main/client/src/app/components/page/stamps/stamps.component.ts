import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-stamps',
  templateUrl: './stamps.component.html',
  styleUrls: ['./stamps.component.css'],
})
export class StampsComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}

  handleStampNewTransit(): void {
    this.router.navigate(['/stamps', 'new']);
  }
}
