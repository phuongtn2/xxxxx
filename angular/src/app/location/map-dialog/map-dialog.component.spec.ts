import { NgModule } from '@angular/core';
import { async, TestBed } from '@angular/core/testing';
import { MdDialog, MdDialogModule } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NguiMapModule } from '@ngui/map';

import { MapDialogComponent } from './map-dialog.component';

@NgModule({
  declarations: [ MapDialogComponent ],
  imports: [
    NguiMapModule,
    NoopAnimationsModule
  ],
  exports: [ MapDialogComponent ],
  entryComponents: [ MapDialogComponent ]
})
class TestModule { }

describe('MapDialogComponent', () => {
  let component: MapDialogComponent;
  let dialog: MdDialog;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        TestModule,
        MdDialogModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    dialog = TestBed.get(MdDialog);
    const dialogRef = dialog.open(MapDialogComponent);
    component = dialogRef.componentInstance;
  });

  afterEach(() => {
    dialog.closeAll();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
