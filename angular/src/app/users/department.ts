import { BaseModel } from '../core/models/base-model';

export class Department extends BaseModel {
  id: number;
  companyId: number;
  name: string;
}
