import { Material } from '../../materials/material';

export class CropMaterial {
  id: number;
  companyId: number;
  registrationId: number;
  materialId: number;
  material: Material;
  quantity: number;
  status: number;
  delFlag: number;
}
