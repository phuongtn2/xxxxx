import { BaseModel } from '../core/models/base-model';
import { MaterialRequestDetail } from './material-request-detail';

export class MaterialRequest extends BaseModel {
  id: number;
  companyId: number;
  registrationId: number;
  employeeRequestId: number;
  employeeRequestName: string;
  requestDate: string;
  approvedStatus: number;
  memo: string;
  requestMaterialDetails: MaterialRequestDetail[];

  static assign(data) {
    const obj = super.assign(data);
    if (Array.isArray(obj.requestMaterialDetails)) {
      obj.requestMaterialDetails = obj.requestMaterialDetails.map(value => Object.assign(new MaterialRequestDetail(), value));
    }
    return obj;
  }
}
