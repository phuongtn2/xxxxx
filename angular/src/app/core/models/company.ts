import { BaseModel } from './base-model';

export class Company extends BaseModel {
  id: number;
  parentId: number;
  name: string;
}
