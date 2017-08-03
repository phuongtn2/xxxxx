import { BaseModel } from '../base-model';

export class Ward extends BaseModel {
  id: number;
  // clientPrimaryKey: number;
  districtId: number;
  name: string;
  type: string;
}
