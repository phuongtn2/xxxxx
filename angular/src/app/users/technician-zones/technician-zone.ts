import { BaseModel } from '../../core/models/base-model';

export class TechnicianZone extends BaseModel {
  id: number;
  employeeId: number;
  cultivationZoneId: number;
  delFlag: number;
}
