import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-image-popup',
  templateUrl: './image-popup.component.html',
  styleUrls: ['./image-popup.component.scss']
})
export class ImagePopupComponent implements OnInit {
  srcUrl: string;

  constructor() { }

  ngOnInit() {
  }
}
