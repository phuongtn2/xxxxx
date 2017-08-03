import { BaseModel } from '../core/models/base-model';
import { FarmerSche } from './schedule/farmer-sche';
import { Actions } from './schedule/actions';
import { Field } from '../fields/field';
import { Nursery } from '../nurseries/nursery';

export class Task extends BaseModel {
  clientPrimaryKey: number;
  id: number;
  cropId: number;
  companyId: number;
  departmentId: number;
  registrationId: number;
  nurseryId: number;
  cultivationId: number;
  title: string;
  description: string;
  startDate: number;
  dueDate: number;
  morningTask: string;
  afternoonTask: string;
  assigneeId: number;
  ownerId: number;
  workStartDate: number;
  workDueDate: number;
  provinceId: number;
  districtId: number;
  wardId: number;
  status: number;
  statusDisplay: boolean;
  nursery: Nursery;
  cultivation: Field;
  farmer: FarmerSche;
  taskReports: Actions[];
  moveActions: Actions[];
}
