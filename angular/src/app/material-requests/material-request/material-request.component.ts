import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/zip';

import { MaterialRequest } from '../material-request';
import { User } from '../../core/models/user';
import { MaterialRequestService } from '../material-request.service';
import { UserService } from '../../users/user.service';

@Component({
  selector: 'app-material-request',
  templateUrl: './material-request.component.html',
  styleUrls: ['./material-request.component.scss'],
  providers: [UserService, MaterialRequestService]
})
export class MaterialRequestComponent implements OnInit {
  title: string;
  materialRequests: MaterialRequest[];
  limit = 10;

  constructor(
    private titleService: Title,
    private userService: UserService,
    private materialRequestService: MaterialRequestService
  ) {
    this.title = 'Yêu cầu vật tư';
  }

  ngOnInit() {
    this.setTitle();
    this.getMaterialRequests();
  }

  getStatus(code) {
    switch (code) {
      case 0:
        return 'Chưa xác nhận';
      case 5:
        return 'Tổ Trưởng xác nhận';
      case 10:
        return 'Thủ Kho xác nhận';
    }
  }

  private getMaterialRequests() {
    Observable.zip(
      this.materialRequestService.getAll(),
      this.userService.getAll(),
      (materialRequests, users) => {
        if (Array.isArray(materialRequests)) {
          materialRequests.map(
            (materialRequest: MaterialRequest) => {
              const user = users.find((u: User) => u.id === materialRequest.employeeRequestId);
              return materialRequest.employeeRequestName = user && user.fullName;
            }
          );
        }
        return materialRequests;
      }
    ).subscribe(
      (materialRequests) => {
        this.materialRequests = materialRequests;
      }
    );
  }

  private setTitle() {
    this.titleService.setTitle(this.title);
  }
}
