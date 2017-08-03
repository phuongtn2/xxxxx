import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { Task as TaskDetail } from '../task';
import { HttpService } from '../../core/services/http.service';
import { Task } from './task';

@Injectable()
export class TaskDetailService {

  constructor(
    private http: HttpService
  ) { }

  sync(): Observable<Task[]> {
    return this.http.get('/api/task/sync')
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }

  getTasks(assigneeId: number): Observable<Task[]> {
    return this.sync()
      .map(
        tasks => {
          return tasks.filter(task => task.assigneeId === assigneeId);
        }
      );
  }

  get(userId: number, taskId: number): Observable<Task> {
    return this.getTasks(userId).map(
      tasks => tasks.find(task => task.id === taskId)
    );
  }

  save(taskDetails: TaskDetail[]): Observable<TaskDetail[]> {
    const requestUrl = '/api/task/sync';
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    return this.http.post(requestUrl, taskDetails, options)
      .map((response: Response) => {
        // TODO handle errors (eg. throw error exception)
        return response.json();
      });
  }
}
