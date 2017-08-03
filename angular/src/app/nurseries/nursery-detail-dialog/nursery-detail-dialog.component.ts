import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MdDialogRef } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { Material } from '../../materials/material';
import { MaterialService } from '../../materials/material.service';
import { Nursery } from '../nursery';
import { NurseryService } from '../nursery.service';
import { NotificationService } from '../../core/services/notification.service';

@Component({
  selector: 'app-nursery-detail-dialog',
  templateUrl: './nursery-detail-dialog.component.html',
  styleUrls: ['./nursery-detail-dialog.component.scss'],
  providers: [
    MaterialService
  ]
})
export class NurseryDetailDialogComponent implements OnInit {
  registrationId: number;

  nurseryDetailForm: FormGroup;
  seeds: Array<Material>;
  nurseryInfo: Nursery;

  constructor(
    private dialogRef: MdDialogRef<NurseryDetailDialogComponent>,
    private fb: FormBuilder,
    private notification: NotificationService,
    private nurseryService: NurseryService,
    private materialService: MaterialService
  ) { }

  ngOnInit() {
    this.buildForm();
    this.getSeeds();
  }

  save() {
    const nursery = this.prepareFormData();
    this.nurseryService.update(this.registrationId, nursery)
      .catch(
        () => {
          this.notification.notify('Có lỗi xảy ra.');
          return Observable.empty();
        }
      )
      .subscribe(
        (newNursery) => {
          this.notification.notify('Đã lưu vườn ươm.');
          this.dialogRef.close(newNursery);
        }
      );
  }

  getSeeds() {
    this.materialService.getSeeds()
      .subscribe(
        (seeds) => {
          this.seeds = seeds;
        }
      );
  }

  private prepareFormData() {
    const formModel = this.nurseryDetailForm.value;
    const nursery = new Nursery();
    nursery.id = formModel.id;
    nursery.address = formModel.address.street;
    nursery.provinceId = formModel.address.provinceId;
    nursery.districtId = formModel.address.districtId;
    nursery.wardId = formModel.address.wardId;
    nursery.seedId = formModel.seedId;

    return nursery;
  }

  private buildForm() {
    this.nurseryDetailForm = this.fb.group({
      id: '',
      address: this.fb.group({
        street: ['', Validators.required],
        provinceId: ['', Validators.required],
        districtId: ['', Validators.required],
        wardId: ['', Validators.required]
      }),
      seedId: ['', Validators.required]
    });

    if (this.nurseryInfo) {
      this.setFormData();
    }
  }

  private setFormData() {
    this.nurseryDetailForm.setValue({
      id: this.nurseryInfo.id,
      address: {
        street: this.nurseryInfo.address,
        provinceId: this.nurseryInfo.provinceId,
        districtId: this.nurseryInfo.districtId,
        wardId: this.nurseryInfo.wardId
      },
      seedId: this.nurseryInfo.seedId
    });
  }
}
