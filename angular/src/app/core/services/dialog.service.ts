import { Injectable, ViewContainerRef } from '@angular/core';
import { MdDialog, MdDialogConfig } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { ConfirmDialogComponent } from '../components/confirm-dialog/confirm-dialog.component';

@Injectable()
export class DialogService {

  constructor(private dialog: MdDialog) { }

  confirm(title: string, message: string, viewContainerRef?: ViewContainerRef): Observable<boolean> {
    const config = new MdDialogConfig();
    if (viewContainerRef) {
      config.viewContainerRef = viewContainerRef;
    }
    const dialogRef = this.dialog.open(ConfirmDialogComponent, config);
    dialogRef.componentInstance.title = title;
    dialogRef.componentInstance.message = message;

    return dialogRef.afterClosed();
  }
}
