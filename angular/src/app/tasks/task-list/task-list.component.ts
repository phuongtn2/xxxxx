import { Component, Input, OnInit } from '@angular/core';
import { MdDialog, MdDialogConfig } from '@angular/material';

import { TaskDetailDialogComponent } from '../task-detail-dialog/task-detail-dialog.component';
import { Task } from '../task';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']
})
export class TaskListComponent implements OnInit {
  @Input() assigneeId;
  @Input() scheduleGroupTimes;
  @Input() compareMonthYear;

  constructor(
    private dialog: MdDialog
  ) { }

  ngOnInit() {
  }

  openDialog(task?: Task) {
    const config = new MdDialogConfig();
    config.width = '600px';
    const dialogRef = this.dialog.open(TaskDetailDialogComponent, config);
    if (task) {
      dialogRef.componentInstance.userId = this.assigneeId;
      dialogRef.componentInstance.schedule = task;
    }
    dialogRef.afterClosed().subscribe((newSchedule) => {
      if (newSchedule) {
        // TODO
        console.log('refresh data table');
      }
    });
  }
}
