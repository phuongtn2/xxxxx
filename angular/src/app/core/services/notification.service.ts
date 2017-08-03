import { Injectable } from '@angular/core';
import { MdSnackBar, MdSnackBarConfig } from '@angular/material';

@Injectable()
export class NotificationService {

  constructor(
    private snackBar: MdSnackBar
  ) { }

  notify(message: string) {
    const config = new MdSnackBarConfig();
    config.duration = 3500;
    this.snackBar.open(message, null, config);
  }
}
