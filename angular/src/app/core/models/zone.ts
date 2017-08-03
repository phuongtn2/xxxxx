import { BaseModel } from './base-model';
import { Province } from './location/province';
import { District } from './location/district';

export class Zone extends BaseModel {
  id: number;
  provinceId: number;
  districtId: number;
  companyId: number;
  employeeId: string;
  cultivationZoneId: number;
  province: Province;
  district: District;
  observerZoneId: string;
}
