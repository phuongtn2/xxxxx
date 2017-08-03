import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MdDialogRef } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { NotificationService } from '../../core/services/notification.service';
import { Material } from '../../materials/material';
import { MaterialService } from '../../materials/material.service';
import { Field } from '../field';
import { FieldService } from '../field.service';

@Component({
  selector: 'app-field-detail-dialog',
  templateUrl: './field-detail-dialog.component.html',
  styleUrls: ['./field-detail-dialog.component.scss'],
  providers: [
    MaterialService
  ]
})
export class FieldDetailDialogComponent implements OnInit {
  registrationId: number;

  fieldDetailForm: FormGroup;
  seeds: Array<Material>;
  fieldInfo: Field;

  constructor(
    private dialogRef: MdDialogRef<FieldDetailDialogComponent>,
    private fb: FormBuilder,
    private notification: NotificationService,
    private materialService: MaterialService,
    private fieldService: FieldService
  ) { }

  ngOnInit() {
    this.buildForm();
    this.getSeeds();
  }

  save() {
    const field = this.prepareFormData();
    this.fieldService.update(this.registrationId, field)
      .catch(
        () => {
          this.notification.notify('Có lỗi xảy ra.');
          return Observable.empty();
        }
      )
      .subscribe(
        (newNursery) => {
          this.notification.notify('Đã lưu thông tin ruộng.');
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
    const formModel = this.fieldDetailForm.value;
    const field = new Field();
    field.id = formModel.id;
    field.address = formModel.address.street;
    field.provinceId = formModel.address.provinceId;
    field.districtId = formModel.address.districtId;
    field.wardId = formModel.address.wardId;
    field.seedId = formModel.seedId;

    return field;
  }

  private buildForm() {
    this.fieldDetailForm = this.fb.group({
      id: '',
      address: this.fb.group({
        street: ['', Validators.required],
        provinceId: ['', Validators.required],
        districtId: ['', Validators.required],
        wardId: ['', Validators.required]
      }),
      seedId: ['', Validators.required]
    });

    if (this.fieldInfo) {
      this.setFormData();
    }
  }

  private setFormData() {
    this.fieldDetailForm.setValue({
      id: this.fieldInfo.id,
      address: {
        street: this.fieldInfo.address,
        provinceId: this.fieldInfo.provinceId,
        districtId: this.fieldInfo.districtId,
        wardId: this.fieldInfo.wardId
      },
      seedId: this.fieldInfo.seedId
    });
  }

}
