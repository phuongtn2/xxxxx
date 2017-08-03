import { isBoolean } from 'util';

import { BaseModel } from '../core/models/base-model';
import { Farmer } from '../core/models/farmer';
import { User } from '../core/models/user';
import { Field } from '../fields/field';
import { Nursery } from '../nurseries/nursery';
import { CropMaterial } from './crop-material-list/crop-material';
import { DryingOven } from './drying-oven-list/drying-oven';

export class Registration extends BaseModel {
  private _isCancel: boolean|number;

  id: number;
  farmerId: number;
  farmer: Farmer;
  companyId: number;
  departmentId: number;
  divisionId: number;
  zoneId: string;
  nurseryId: number;
  cultivationId: number;
  responsibilityEmployeeId: number;
  responsibilityEmployee: User;
  registrationAcreage: number;
  householdMembers: number;
  exp: number;
  labors: number;
  soilType: number;
  cultivationAcreage: number;
  memo: string;
  field: Field;
  nursery: Nursery;
  cropMaterials: CropMaterial[];
  dryingOvens: DryingOven[];

  get isCancel(): boolean|number {
    let isCancel: boolean|number = this._isCancel;
    if (isBoolean(isCancel)) {
      isCancel = isCancel ? 1 : 0;
    }
    return isCancel;
  }

  set isCancel(value: boolean|number) {
    this._isCancel = value;
  }

  get canCancel() {
    // already canceled?
    if (this.isCancel !== 0) {
      return false;
    }

    // can cancel the registration have not been registered
    if (!(this.cultivationId || this.nurseryId)) {
      return true;
    }
    // cannot cancel when data might corrupted
    if (!(this.field || this.nursery)) {
      return false;
    }
    // cannot cancel when having field plot already
    if (this.field && this.field.fieldPlots && this.field.fieldPlots.length) {
      return false;
    }
    // cannot cancel when nursery have been updated
    if (this.nursery && this.nursery.seedingDate) {
      return false;
    }

    // otherwise:
    // - have field but not have field plot
    // - have nursery but not have been updated in mobile
    return true;
  }

  get status() {
    return this.isCancel ? 'Hủy đăng ký' : 'Đã đăng ký';
  }

  static assign(data) {
    const obj = super.assign(data);
    if (Array.isArray(obj.dryingOvens)) {
      obj.dryingOvens = obj.dryingOvens.map(value => DryingOven.assign(value));
    }
    return obj;
  }
}
