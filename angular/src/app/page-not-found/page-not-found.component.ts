import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.scss']
})
export class PageNotFoundComponent implements OnInit {
  private title: string;

  constructor(
    private titleService: Title,
    private location: Location
  ) {
    this.title = 'Lỗi 404';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
  }

  goBack() {
    this.location.back();
  }
}
