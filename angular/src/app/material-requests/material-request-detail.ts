import { BaseModel } from '../core/models/base-model';
import { Material } from '../materials/material';

export class MaterialRequestDetail extends BaseModel {
  id: number;
  requestMaterialId: number;
  materialId: number;
  requestQuantity: number;
  approverLv1Id: number;
  approveLv1Quantity: number;
  approvedLv1Date: number;
  approverLv2Id: number;
  approveLv2Quantity: number;
  approvedLv2Date: number;
  material: Material;

  get approvedQuantity(): number {
    return this.approveLv2Quantity || this.approveLv1Quantity;
  }
}
