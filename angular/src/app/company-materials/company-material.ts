import { Material } from '../materials/material';

export class CompanyMaterial extends Material {
  id: number;
  companyId: string;
  materialId: string;
  status: string;
  price: number;
  memo: string;
}
