import { Component, OnInit } from '@angular/core';
import { MdDialog, MdDialogRef } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';

import { LocationService } from '../../core/services/location.service';
import { Task } from '../task';

@Component({
  selector: 'app-task-detail-dialog',
  templateUrl: './task-detail-dialog.component.html',
  styleUrls: ['./task-detail-dialog.component.scss'],
  providers: [LocationService]
})
export class TaskDetailDialogComponent implements OnInit {
  title: string;
  userId: number;
  schedule: Task;
  address: string[];

  constructor(
    private titleService: Title,
    private dialogRef: MdDialogRef<TaskDetailDialogComponent>,
    private locationService: LocationService,
    private dialog: MdDialog,
    private router: Router
  ) {
    this.title = 'Chi tiết công việc';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.schedule = this.schedule || new Task();
    this.getLocation();
  }

  getLocation() {
    this.address = new Array(3);
    let provinceId, districtId, wardId;
    if (this.schedule.cultivation) {
      provinceId = this.schedule.cultivation.provinceId;
      districtId = this.schedule.cultivation.districtId;
      wardId = this.schedule.cultivation.wardId;
    } else if (this.schedule.nursery) {
      provinceId = this.schedule.nursery.provinceId;
      districtId = this.schedule.nursery.districtId;
      wardId = this.schedule.nursery.wardId;
    } else {
      provinceId = this.schedule.provinceId;
      districtId = this.schedule.districtId;
      wardId = this.schedule.wardId;
    }

    this.locationService.getProvince(this.schedule.provinceId).subscribe(
      province => {
        this.address[2] = province.name;
        this.locationService.getDistrict(districtId, provinceId).subscribe(
          district => {
            this.address[1] = district.name;
            this.locationService.getWard(wardId, districtId).subscribe(
              ward => {
                this.address[0] = ward.name;
              }
            );
          }
        );
      }
    );
  }

  editTask() {
    this.dialogRef.close();
    this.router.navigate(['/user', this.userId, 'schedule', this.schedule.id]);
  }
}
