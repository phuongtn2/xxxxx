import { BaseModel } from '../base-model';

export class District extends BaseModel {
  id: number;
  // clientPrimaryKey: number;
  provinceId: number;
  name: string;
  type: string;
}
