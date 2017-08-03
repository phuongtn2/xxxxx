import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-mini-map',
  templateUrl: './mini-map.component.html',
  styleUrls: ['./mini-map.component.scss']
})
export class MiniMapComponent implements OnInit {
  @Input() location;

  constructor() { }

  ngOnInit() {
  }
}
