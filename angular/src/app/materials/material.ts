import { BaseModel } from '../core/models/base-model';
import { MaterialSubType } from '../core/enum/material-sub-type.enum';
import { MaterialType } from '../core/enum/material-type.enum';

export class Material extends BaseModel {
  id: number;
  sku: string;
  name: string;
  unit: string;
  type: MaterialType;
  subType: MaterialSubType;

  // TODO: cheat everywhere
  get typeStr() {
    let str: string;
    if (this.type) {
      if (this.type.toString() === MaterialType.NonEquipment.toString()) {
        str = 'Vật tư nông nghiệp';
        const sub = this.subTypeStr;
        if (sub) {
          str = sub;
        }
      }
      if (this.type.toString() === MaterialType.Equipment.toString()) {
        str = 'Vật tư khác';
      }
    }
    return str;
  }

  // TODO: cheat everywhere v2
  get subTypeStr() {
    let str: string;
    if (this.subType) {
      if (this.subType.toString() === MaterialSubType.Seed.toString()) {
        str = 'Giống';
      }
      if (this.subType.toString() === MaterialSubType.Pesticide.toString()) {
        str = 'Thuốc trừ sâu';
      }
      if (this.subType.toString() === MaterialSubType.Fertilizer.toString()) {
        str = 'Phân bón';
      }
    }
    return str;
  }
}
