import { BaseModel } from '../core/models/base-model';
import { PlotDetail } from './plot-detail';
import { FieldAction } from '../core/enum/field-action.enum';
import { Content } from './content';

export class FieldPlot extends BaseModel {
  clientPrimaryKey: number;
  id: number;
  name: string;
  acreage: number;
  registrationId: number;
  cultivationId: number;
  cultivationDate: number;
  densityRow: number;
  densityColumn: number;
  plantRowRatio: number;
  rowPlotRatio: number;
  leafsRatio: number;
  details: PlotDetail[];

  get pestDetails() {
    return this.details.filter(detail => detail.action && detail.action.toString() === FieldAction.Pest.toString());
  }

  get pestContents() {
    const contents: Content[] = [];
    this.pestDetails.forEach(
      detail => {
        detail.contents.forEach(
          content => {
            if (!contents.find(c => c.pestId === content.pestId)) {
              contents.push(content);
            }
          }
        );
      }
    );
    return contents;
  }

  static assign(data) {
    const obj = super.assign(data);
    if (Array.isArray(obj.details)) {
      obj.details = obj.details.map(value => PlotDetail.assign(value));
    }
    return obj;
  }
}
