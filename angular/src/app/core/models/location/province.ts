import { BaseModel } from '../base-model';

export class Province extends BaseModel {
  id: number;
  // clientPrimaryKey: number;
  name: string;
  type: string;
}
