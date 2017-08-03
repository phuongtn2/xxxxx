import { BaseModel } from '../core/models/base-model';
import { FieldAction } from '../core/enum/field-action.enum';
import { Content } from './content';
import { MaterialSupplement } from './material-supplement';
import { Media } from './media';

export class PlotDetail extends BaseModel {
  clientPrimaryKey: number;
  id: number;
  registrationId: number;
  cultivationId: number;
  fieldPlotId: number;
  action: FieldAction;
  actionName: string;
  actionUpdateDate: number;
  actionIndex: number;
  actionDate: number;
  latitude: number;
  longitude: number;
  responsibilityEmployeeId: number;
  target: string;
  resultTarget: string;
  statusQuo: string;
  recommend: string;
  medias: Media[];
  contents: Content[];
  materialSupplements: MaterialSupplement[];
}
