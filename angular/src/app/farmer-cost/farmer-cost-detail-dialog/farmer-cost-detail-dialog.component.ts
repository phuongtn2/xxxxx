import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MdDialogRef } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs/Observable';

import { ActionType, ActionTypes, CostType, CostTypes } from '../../core/enum/cost-type.enum';
import { AuthenticationService } from '../../core/services/authentication.service';
import { NotificationService } from '../../core/services/notification.service';
import { Registration } from '../../crop-session/registration';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { Material } from '../../materials/material';
import { MaterialService } from '../../materials/material.service';
import { FarmerCost } from '../farmer-cost';
import { FarmerCostService } from '../farmer-cost.service';

@Component({
  selector: 'app-farmer-cost-detail-dialog',
  templateUrl: './farmer-cost-detail-dialog.component.html',
  styleUrls: ['./farmer-cost-detail-dialog.component.scss'],
  providers: [CropSessionService, MaterialService]
})
export class FarmerCostDetailDialogComponent implements OnInit {
  title: string;
  farmerCost: FarmerCost;
  registrations: Registration[];
  farmerCostForm: FormGroup;
  materials: Material[];
  filteredMaterials: Observable<Material[]>;

  get CostTypes() { return CostTypes; }

  get ActionTypes() { return ActionTypes; }

  formErrors = {
    registrationId: '',
    costType: '',
    actionType: '',
    costTitle: '',
    quantity: '',
    price: ''
  };

  private validationMessages = {
    registrationId: {
      required: 'Cần chọn Nông Dân.'
    },
    costType: {
      required: 'Cần nhập Loại.'
    },
    actionType: {
      required: 'Cần nhập Tên.'
    },
    costTitle: {
      required: 'Cần nhập Chi Tiết.'
    },
    quantity: {
      required: 'Cần nhập Số Lượng.',
      pattern: 'Số Lượng không hợp lệ.'
    },
    price: {
      required: 'Cần nhập Giá.',
      pattern: 'Giá không hợp lệ.'
    }
  };

  constructor(
    private titleService: Title,
    private dialogRef: MdDialogRef<FarmerCostDetailDialogComponent>,
    private fb: FormBuilder,
    private authService: AuthenticationService,
    private notification: NotificationService,
    private materialService: MaterialService,
    private cropSessionService: CropSessionService,
    private farmerCostService: FarmerCostService
  ) { }

  ngOnInit() {
    this.buildForm();

    this.title = 'Thêm chi phí nông dân';
    if (this.farmerCost) {
      this.setFormData();
      this.title = 'Sửa chi phí nông dân';
    }
    this.titleService.setTitle(this.title);

    this.cropSessionService.getByResponsibilityEmployee(this.authService.currentUser.id)
      .subscribe(
        registrations => this.registrations = registrations
      );

    this.materialService.getAll().subscribe(
      materials => {
        this.materials = materials;
        this.filteredMaterials = this.farmerCostForm.get('costTitle').valueChanges
          .startWith(null)
          .map(val => {
            if (this.farmerCostForm.get('costType').value !== CostType.Material.value) {
              return null;
            }
            return val ? this.filterMaterials(val) : this.materials;
          });
      }
    );
  }

  saveFarmerCost() {
    const cost = this.prepareData();
    this.farmerCostService.saveCost(cost)
      .subscribe(
        () => {
          this.notification.notify('Cập nhật thành công.');
          this.dialogRef.close(true);
        }
      );
  }

  private setFormData() {
    this.farmerCostForm.setValue({
      id: this.farmerCost.id,
      registrationId: this.farmerCost.registrationId,
      costType: this.farmerCost.type,
      actionType: this.farmerCost.actionType,
      costTitle: this.farmerCost.title,
      quantity: this.farmerCost.quantity,
      price: this.farmerCost.price
    });
  }

  private prepareData() {
    const formModel = this.farmerCostForm.value;
    const farmerCost = new FarmerCost();
    farmerCost.id = formModel.id;
    farmerCost.registrationId = formModel.registrationId;
    farmerCost.type = formModel.costType;
    farmerCost.actionType = formModel.actionType;
    farmerCost.title = formModel.costTitle;
    farmerCost.quantity = formModel.quantity;
    farmerCost.price = formModel.price;
    return farmerCost;
  }

  private filterMaterials(val: any) {
    val = val.toString().replace(/[(){\[.]/g, function (m) {
      return '\\' + m;
    });
    const regexp = new RegExp(val, 'i');
    return this.materials.filter((material) => {
      return regexp.test(material.name);
    });
  }

  private buildForm() {
    this.farmerCostForm = this.fb.group({
      id: [null],
      registrationId: [null, Validators.required],
      costType: [null, Validators.required],
      actionType: [null, Validators.required],
      costTitle: [null, Validators.required],
      quantity: [null, [Validators.required, Validators.pattern(/^[1-9]\d*$/)]],
      price: [null, [Validators.required, Validators.pattern(/^[1-9]\d*$/)]]
    });

    this.farmerCostForm.valueChanges.subscribe(() => this.onValueChanges());
    this.farmerCostForm.get('costType').valueChanges
      .subscribe(
        value => {
          if (value === CostType.Other.value) {
            this.farmerCostForm.get('actionType').setValue(ActionType.Other.value);
          } else if (this.farmerCostForm.get('actionType').value === ActionType.Other.value) {
            this.farmerCostForm.get('actionType').setValue('');
          }
        }
      );
  }

  private onValueChanges() {
    if (!this.farmerCostForm) {
      return;
    }
    const form = this.farmerCostForm;

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
