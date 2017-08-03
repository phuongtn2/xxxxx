import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { HttpService } from '../core/services/http.service';
import { Task } from './task';
import { ScheduleUpdate } from './schedule/schedule-update';

@Injectable()
export class TaskService {
  scheduleUpdate: ScheduleUpdate;
  scheduleUpdates: ScheduleUpdate[];

  constructor(private http: HttpService) { }

  sync(): Observable<Task[]> {
    return this.http.get('/api/task/sync')
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        const json = response.json();
        if (Array.isArray(json)) {
          return json.map(value => Task.assign(value));
        }
        return [];
      });
  }

  getSchedule(assigneeId: number): Observable<Task[]> {
    return this.sync()
      .map(
        tasks => {
          // TODO will change those class name
          return tasks.filter(task => task.assigneeId === assigneeId);
        }
      );
  }

  updateSchedule(schedule: Task): Observable<Task> {
    const requestUrl = '/api/task/sync';
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    this.scheduleUpdates = [];
    this.scheduleUpdate = new ScheduleUpdate();
    this.scheduleUpdate.clientPrimaryKey = 0;
    this.scheduleUpdate.registrationId = schedule.registrationId;
    this.scheduleUpdate.id = schedule.id;
    this.scheduleUpdate.nurseryId = schedule.nurseryId;
    this.scheduleUpdate.cultivationId = schedule.cultivationId;
    this.scheduleUpdate.title = schedule.title;
    this.scheduleUpdate.startDate = schedule.startDate;
    this.scheduleUpdate.dueDate = schedule.dueDate;
    this.scheduleUpdates.push(this.scheduleUpdate);

    return this.http.post(requestUrl, this.scheduleUpdates, options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }

  get(userId: number, taskId: number): Observable<Task> {
    return this.getSchedule(userId).map(
      tasks => tasks.find(task => task.id === taskId)
    );
  }
}
