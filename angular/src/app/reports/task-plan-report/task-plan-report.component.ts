import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';

import { User } from '../../core/models/user';
import { UserService } from '../../users/user.service';
import { TaskPlanReport } from './models/task-plan-report';
import { TaskPlanReportService } from './task-plan-report.service';

@Component({
  selector: 'app-task-plan-report',
  templateUrl: './task-plan-report.component.html',
  styleUrls: [
    '../report-data-table.scss',
    './task-plan-report.component.scss'
  ],
  providers: [TaskPlanReportService, UserService]
})
export class TaskPlanReportComponent implements OnInit {
  title: string;
  data = new TaskPlanReport();
  form: FormGroup;
  technicians: Array<User>;

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private userService: UserService,
    private taskPlanReportService: TaskPlanReportService
  ) {
    this.title = 'Báo cáo kế hoạch làm việc';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);

    this.form = this.fb.group({
      technicianId: [null, Validators.required],
      dateReport: [1],
      currentSpeedometer: [1]
    });
    this.form.get('technicianId').valueChanges
      .debounceTime(1000)
      .subscribe(
        () => {
          this.data.reset();
          this.taskPlanReportService.getData(this.form.get('technicianId').value)
            .subscribe(
              (data) => {
                this.data.parse(data);
              }
            );
        }
      );
    this.userService.getResponsibilityEmployees()
      .subscribe(
        (users) => {
          this.technicians = users;
        }
      );
  }

  exportReport() {
    const formModel = this.form.value;
    this.taskPlanReportService.exportReport(formModel.technicianId);
  }
}
