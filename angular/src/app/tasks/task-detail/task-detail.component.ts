import { Component, OnInit } from '@angular/core';
import { DatePipe, Location } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Response } from '@angular/http';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';
import 'rxjs/add/observable/zip';

import { User } from '../../core/models/user';
import { Task as TaskDetail } from '../task';
import { NotificationService } from '../../core/services/notification.service';
import { Registration } from '../../crop-session/registration';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { FieldService } from '../../fields/field.service';
import { NurseryService } from '../../nurseries/nursery.service';
import { UserService } from '../../users/user.service';
import { Task } from './task';
import { TaskDetailService } from './task-detail.service';

@Component({
  selector: 'app-task-detail',
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.scss'],
  providers: [UserService, CropSessionService, TaskDetailService, DatePipe]
})
export class TaskDetailComponent implements OnInit {
  title: string;
  taskDetailInfo: FormGroup;
  user: User;
  task: Task;
  registrations: Array<Registration>;
  userId: number;

  selectedRegistration: Registration;

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private location: Location,
    private userService: UserService,
    private route: ActivatedRoute,
    private notification: NotificationService,
    private cropSessionService: CropSessionService,
    private fieldService: FieldService,
    private nurseryService: NurseryService,
    private taskDetailService: TaskDetailService,
    private router: Router,
    private datePipe: DatePipe
  ) {
    this.title = 'Chi Tiết Công Việc';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    const id = +this.route.snapshot.params['userId'];
    this.userId = id;
    this.taskDetailInfo = this.fb.group({
      id: [],
      taskType: [null],
      target: '',
      title: '',
      description: '',
      startDate: '',
      dueDate: '',
      registrationId: '',
      morningTask: '',
      afternoonTask: '',
      address: this.fb.group({
        provinceId: ['', Validators.required],
        districtId: ['', Validators.required],
        wardId: ['', Validators.required]
      })
    });

    this.userService.get(id)
      .subscribe(
        (employee: User) => {
          // TODO fix this
          if (employee instanceof ErrorObservable) {
            return false;
          }
          this.user = employee;

          this.cropSessionService.getByResponsibilityEmployee(this.user.id)
            .subscribe(
              (registrations: Array<Registration>) => {
                this.registrations = registrations;

                // TODO(dtrthi): missing case đổi user quản lý nông dân
                const taskId = +this.route.snapshot.params['taskId'];
                if (taskId) {
                  this.taskDetailService.get(this.user.id, taskId)
                    .subscribe(
                      task => {
                        if (task) {
                          this.task = task;
                          this.onRegistrationSelect(task.registrationId);
                          this.setFormData();
                        } else {
                          // TODO navigate 404 page
                        }
                      }
                    );
                }
              }
            );
        }
      );
  }

  goBack() {
    this.location.back();
  }

  onRegistrationSelect(registrationId: number) {
    this.selectedRegistration = this.getRegistrationById(registrationId);
    this.taskDetailInfo.get('target').reset();
  }

  getRegistrationById(registrationId: number): Registration {
    return this.registrations.find(registration => registration.id === registrationId);
  }

  private setFormData() {
    let taskType = 2;
    let target = null;
    if (this.task.cultivationId || this.task.nurseryId) {
      taskType = 1;
      if (this.task.cultivationId) {
        target = 1;
      } else if (this.task.nurseryId) {
        target = 2;
      }
    }

    this.taskDetailInfo.patchValue({
      id: this.task.id,
      taskType: taskType,
      target: target,
      title: this.task.title,
      description: this.task.description,
      startDate: this.datePipe.transform(this.task.startDate, 'yyyy-MM-dd'),
      dueDate: this.datePipe.transform(this.task.dueDate, 'yyyy-MM-dd'),
      registrationId: this.task.registrationId,
      morningTask: this.task.morningTask,
      afternoonTask: this.task.afternoonTask,
      address: {
        provinceId: this.task.provinceId,
        districtId: this.task.districtId,
        wardId: this.task.wardId
      }
    });
  }

  private prepareTaskDetail(): Task {
    const formModel = this.taskDetailInfo.value;
    const taskData = new Task();
    taskData.id = formModel.id;
    taskData.assigneeId = this.userId;
    taskData.ownerId = this.userId;
    taskData.status = 0;

    taskData.title = formModel.title;
    const startDate = new Date(formModel.startDate);
    taskData.startDate = startDate.getTime();
    const dueDate = new Date(formModel.dueDate);
    taskData.dueDate = dueDate.getTime();
    taskData.description = formModel.description;

    // Công việc vườn ươm/đồng ruộng taskType = 1
    if (formModel.taskType === 1) {
      taskData.morningTask = '';
      taskData.afternoonTask = '';
      const registration = this.selectedRegistration;
      taskData.registrationId = formModel.registrationId;
      if (formModel.target === '1') {
        taskData.cultivationId = 0;
        // get nurseryId from registrations
        if (registration) {
          taskData.nurseryId = registration.nurseryId;
        }
      } else {
        taskData.nurseryId = 0;
        if (registration) {
          taskData.cultivationId = registration.cultivationId;
        }
      }
    } else {
      taskData.registrationId = formModel.registrationId;
      taskData.morningTask = formModel.morningTask;
      taskData.afternoonTask = formModel.afternoonTask;
      taskData.provinceId = formModel.address.provinceId;
      taskData.districtId = formModel.address.districtId;
      taskData.wardId = formModel.address.wardId;
      taskData.cultivationId = 0;
      taskData.nurseryId = 0;
    }

    return taskData;
  }

  saveTask() {
    this.task = this.prepareTaskDetail();
    if (this.task.cultivationId) {
      this.fieldService.getField(this.task.registrationId, this.task.cultivationId)
        .subscribe(
          field => {
            this.task.provinceId = field.provinceId;
            this.task.districtId = field.districtId;
            this.task.wardId = field.wardId;
            this.savePreparedTask();
          }
        );
    } else if (this.task.nurseryId) {
      this.nurseryService.getNursery(this.task.registrationId, this.task.nurseryId)
        .subscribe(
          nursery => {
            this.task.provinceId = nursery.provinceId;
            this.task.districtId = nursery.districtId;
            this.task.wardId = nursery.wardId;
            this.savePreparedTask();
          }
        );
    } else {
      this.savePreparedTask();
    }
  }

  private savePreparedTask() {
    const listTask = [];
    const newTask = TaskDetail.assign(this.task);
    listTask.push(newTask);
    this.taskDetailService.save(listTask)
      .catch(
        (response: Response) => {
          this.notification.notify('Có lỗi xảy ra.');
          return Observable.throw(response);
        }
      )
      .subscribe(
        () => {
          this.notification.notify('Lưu thành công');
          this.router.navigate([`/user/${this.userId}/schedule`]);
        }
      );
  }
}
