import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs/Observable';

import { User } from '../../core/models/user';
import { UserService } from '../../users/user.service';
import { TaskMoveActionReport } from './models/task-move-action-report';
import { TaskMoveActionReportService } from './task-move-action-report.service';

@Component({
  selector: 'app-task-move-action-report',
  templateUrl: './task-move-action-report.component.html',
  styleUrls: [
    '../report-data-table.scss',
    './task-move-action-report.component.scss'
  ],
  providers: [DatePipe, TaskMoveActionReportService, UserService]
})
export class TaskMoveActionReportComponent implements OnInit {
  title: string;
  data = new TaskMoveActionReport();
  form: FormGroup;
  technicians: Array<User>;

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private userService: UserService,
    private taskMoveActionService: TaskMoveActionReportService
  ) {
    this.title = 'Báo cáo km sử dụng xe máy';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);

    this.form = this.fb.group({
      technicianId: ['', Validators.required],
      startDate: [this.datePipe.transform(Date.now(), 'yyyy-MM-dd'), Validators.required],
      endDate: [this.datePipe.transform(Date.now(), 'yyyy-MM-dd'), Validators.required],
      licensePlate: [1],
      zoneName: [1]
    });
    this.form.get('technicianId').valueChanges
      .debounceTime(1000)
      .subscribe(this.onValueChanges());
    this.form.get('startDate').valueChanges
      .debounceTime(1000)
      .subscribe(this.onValueChanges());
    this.form.get('endDate').valueChanges
      .debounceTime(1000)
      .subscribe(this.onValueChanges());

    this.userService.getResponsibilityEmployees()
      .subscribe(
        (users) => {
          this.technicians = users;
        }
      );
  }

  exportReport() {
    const formModel = this.form.value;
    this.taskMoveActionService.exportReport(formModel.technicianId, formModel.startDate, formModel.endDate);
  }

  private onValueChanges() {
    return () => {
      this.data.reset();
      this.taskMoveActionService.getData(
        this.form.get('technicianId').value,
        this.form.get('startDate').value,
        this.form.get('endDate').value
      ).catch(
        (response) => {
          return Observable.throw(response);
        }
      )
      .subscribe(
        (data) => {
          this.data.parse(data);
        }
      );
    };
  }
}
