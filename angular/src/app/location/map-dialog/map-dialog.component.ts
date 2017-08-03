import { Component, OnInit } from '@angular/core';
import { MdDialogRef } from '@angular/material';

@Component({
  selector: 'app-map-dialog',
  templateUrl: './map-dialog.component.html',
  styleUrls: ['./map-dialog.component.scss']
})
export class MapDialogComponent implements OnInit {
  location;

  constructor(
    private dialogRef: MdDialogRef<MapDialogComponent>
  ) { }

  ngOnInit() {
    this.dialogRef.updateSize('80%', '90%');
  }
}
