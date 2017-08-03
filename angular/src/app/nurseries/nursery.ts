import { BaseModel } from '../core/models/base-model';
import { NurseryActionDetail } from './nursery-detail/nursery-action-detail';
import { NurseryAction } from '../core/enum/nursery-action.enum';

export class Nursery extends BaseModel {
  id: number;
  registrationId: number;
  acreage: number;
  density: number;
  address: string;
  provinceId: number;
  districtId: number;
  wardId: number;
  seedingDate?: number;
  exchangeDate?: number;
  exchangeRatio: number;
  skinRatio: number;
  trayRatio: number;
  quantity: number;
  seedId: number;

  get fullAddress() {
    return `${this.wardId} - ${this.districtId} - ${this.provinceId}`;
  }

  private _hasAbility;
  get hasAbility(): string {
    return this._hasAbility ? 'Đủ' : 'Thiếu';
  }
  set hasAbility(value) {
    this._hasAbility = value;
  }

  details: NurseryActionDetail[] = [];

  get pestContents() {
    const details = this.details.filter(detail => detail.action && detail.action.toString() === NurseryAction.Pest.toString());

    const contents = [];
    details.forEach(
      detail => {
        detail.contents.forEach(
          content => {
            if (!detail.contents.find(c => c.pestId === content.pestId)) {
              contents.push(content);
            }
          }
        );
      }
    );
    return contents;
  }
}
