import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

import { AuthenticationService } from '../../core/services/authentication.service';
import { TaskService } from '../task.service';
import { Types } from './types';
import { ScheduleGroupTime } from './schedule_group_time';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.scss'],
  providers: [TaskService]
})
export class ScheduleComponent implements OnInit {
  title: string;
  type = 1;
  date: Date;
  types: Types[];
  userId: number;
  scheduleGroupTimes: ScheduleGroupTime[];
  compareMonthYear: string;
  month: number;
  year: number;
  dayDisplay: string;

  constructor(
    private titleService: Title,
    private location: Location,
    private route: ActivatedRoute,
    private authService: AuthenticationService,
    private taskService: TaskService
  ) {
    this.title = 'Chi Tiết Lịch Trình';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);

    this.changeMonth(0);
    this.types = [
      {'id': 1, 'name': 'Tháng'},
      {'id': 2, 'name': 'Công Viêc'}
    ];
    this.userId = +this.route.snapshot.params['userId'];
    if (!this.userId && this.authService.currentUser) {
      this.userId = this.authService.currentUser.id;
    }
    this.getSchedule(this.userId);
  }

  getSchedule(assigneeId: number) {
    this.taskService.getSchedule(assigneeId).subscribe(
      tasks => {
        this.scheduleGroupTimes = [];
        const temp = tasks;
        const dayTask = [];
        for (let i = 0; i < temp.length; i++) {
          const task = temp[i];
          if (task) {
            const date = new Date(task.startDate);
            date.setHours(0, 0, 0, 0);
            dayTask.push(`${date.getTime()}`);

            const scheduleGroupTime = new ScheduleGroupTime();
            scheduleGroupTime.date = date;
            scheduleGroupTime.strDate = date.toLocaleDateString();
            scheduleGroupTime.monthAndYear = `${date.getMonth() + 1}${date.getFullYear()}`;
            const listTask = [];
            listTask.push(task);
            for (let j = i + 1; j < tasks.length; j++) {
              if (tasks[j] && task.startDate === tasks[j].startDate) {
                listTask.push(tasks[j]);
                delete temp[j];
              }
            }
            scheduleGroupTime.listTask = listTask;
            this.scheduleGroupTimes.push(scheduleGroupTime);
          }
        }
        sessionStorage.setItem('dayTask', JSON.stringify(dayTask));
      }
    );
  }

  onSelectDay(event) {
    console.log('Event', event);
    this.compareMonthYear = new Date(event).toLocaleDateString();
  }

  changeMonth(value) {
    if (value === -1) {
      if (this.month === 1) {
        this.year = this.year - 1;
        this.month = 12;
      } else {
        this.month = this.month - 1;
      }
      this.dayDisplay = `Tháng ${this.month} Năm ${this.year}`;
    } else if (value === 1) {
      if (this.month === 12) {
        this.year = this.year + 1;
        this.month = 1;
      } else {
        this.month = this.month + 1;
      }
      this.dayDisplay = `Tháng ${this.month} Năm ${this.year}`;
    } else {
      const day =  new Date(Date.now());
      this.month = day.getMonth() + 1;
      this.year = day.getFullYear();
      this.dayDisplay = `Tháng ${this.month} Năm ${this.year}`;
    }
    this.compareMonthYear = `${this.month}${this.year}`;
  }

  goBack() {
    this.location.back();
  }
}
