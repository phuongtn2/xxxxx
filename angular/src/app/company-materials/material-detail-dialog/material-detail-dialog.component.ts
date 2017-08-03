import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Response } from '@angular/http';
import { MdDialogRef } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { requiredClass } from '../../core/validators/required-class';
import { Material } from '../../materials/material';
import { CompanyMaterial } from '../company-material';
import { MaterialService } from '../../materials/material.service';
import { CompanyMaterialService } from '../company-material.service';
import { NotificationService } from '../../core/services/notification.service';

@Component({
  selector: 'app-material-detail-dialog',
  templateUrl: './material-detail-dialog.component.html',
  styleUrls: ['./material-detail-dialog.component.scss'],
  providers: [MaterialService, CompanyMaterialService]
})
export class MaterialDetailDialogComponent implements OnInit {
  title: string;
  materials: Material[];
  filteredMaterials: Observable<Material[]>;
  material: CompanyMaterial;
  materialInfo: FormGroup;
  isEditing = false;

  formErrors = {
    material: '',
    price: ''
  };

  private validationMessages = {
    material: {
      required: 'Cần chọn Vật tư.',
      requiredClass: 'Chỉ chọn Vật tư trong danh sách.'
    },
    price: {
      required: 'Cần nhập Giá.',
      pattern: 'Giá không hợp lệ.'
    }
  };

  constructor(
    private dialogRef: MdDialogRef<MaterialDetailDialogComponent>,
    private fb: FormBuilder,
    private materialService: MaterialService,
    private notification: NotificationService,
    private companyMaterialService: CompanyMaterialService
  ) { }

  ngOnInit() {
    this.title = 'Thêm vật tư';
    this.buildForm();
    if (this.material) {
      this.setFormData();
      this.title = 'Sửa vật tư';
    }

    this.materialService.getAll().subscribe(
      materials => {
        this.materials = materials;
        this.filteredMaterials = this.materialInfo.get('material').valueChanges
          .startWith(this.materialInfo.get('material').value)
          .map(val => {
            if (typeof val === 'object') {
              this.onSelectMaterial();
            }
            return val ? this.filter(val) : this.materials.slice();
          });
      }
    );
  }

  private setFormData() {
    this.materialInfo.setValue({
      id: this.material.id,
      materialId: this.material.materialId,
      material: this.material,
      price: this.material.price,
      memo: this.material.memo,
      typeStr: this.material.typeStr,
      unit: this.material.unit
    });
    this.isEditing = true;
    this.materialInfo.get('material').disable();
  }

  saveMaterial() {
    this.material = this.prepareMaterial();
    this.companyMaterialService.save(this.material)
      .catch(
        (response: Response) => {
          this.notification.notify('Có lỗi xảy ra.');
          return Observable.throw(response);
        }
      )
      .subscribe(
        () => {
          this.notification.notify('Lưu thành công.');
          this.dialogRef.close(true);
        }
      );
  }

  private prepareMaterial(): CompanyMaterial {
    const formModel = this.materialInfo.value;
    const material = new CompanyMaterial();
    material.id = formModel.id;
    material.materialId = formModel.material ? formModel.material.id : this.material.materialId;
    material.price = formModel.price;
    material.memo = formModel.memo;
    return material;
  }

  onSelectMaterial() {
    this.materialInfo.patchValue({
      unit: '',
      typeStr: ''
    });

    const material = this.materialInfo.get('material').value;
    if (typeof material === 'object') {
      this.materialInfo.patchValue({
        unit: material.unit,
        typeStr: material.typeStr
      });
    }
  }

  //noinspection JSMethodCanBeStatic
  displayMaterialWith(material: Material) {
    return material ? material.name : material;
  }

  private filter(val: any) {
    val = val.toString().replace(/[(){\[.]/g, function (m) {
      return '\\' + m;
    });
    const regexp = new RegExp(val, 'i');
    return this.materials.filter((material) => {
      return regexp.test(material.name);
    });
  }

  private buildForm() {
    this.materialInfo = this.fb.group({
      id: [''],
      materialId: [''],
      material: ['', [Validators.required, requiredClass(Material)]],
      price: ['', [Validators.required, Validators.pattern(/^[1-9]\d*$/)]],
      memo: [''],
      typeStr: [{value: '', disabled: true}],
      unit: [{value: '', disabled: true}]
    });

    this.materialInfo.valueChanges
      .subscribe(() => this.onValueChanges());
  }

  private onValueChanges() {
    if (!this.materialInfo) {
      return;
    }
    const form = this.materialInfo;

    for (const field of Object.keys(this.formErrors)) {
      // clear previous error message if any
      this.formErrors[field] = '';
      const control = form.get(field);

      if (control && control.invalid) {
        const messages = this.validationMessages[field];
        const keys = Object.keys(control.errors);
        if (!keys.length) {
          continue;
        }
        const key = keys.shift();
        this.formErrors[field] = messages[key];
      }
    }
  }
}
