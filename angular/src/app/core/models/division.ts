import { BaseModel } from './base-model';

export class Division extends BaseModel {
  id?: number;
  accountantId: number;
  clientPrimaryKey: number;
  companyId: number;
  departmentId: number;
  managerId: number;
  name: string;
  storeKeeperId: number;
  subManagerId: number;
}
