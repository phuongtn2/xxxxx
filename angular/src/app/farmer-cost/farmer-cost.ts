import { BaseModel } from '../core/models/base-model';
import { ActionType, ActionTypes, CostType, CostTypes } from '../core/enum/cost-type.enum';
import { Registration } from '../crop-session/registration';

export class FarmerCost extends BaseModel {
  clientPrimaryKey: number;
  id: number;
  companyId: number;
  cropId: number;
  registrationId: number;
  registration: Registration;
  type: CostType;
  actionType: ActionType;
  title: string;
  actionId: number;
  quantity: number;
  price: number;

  get typeStr() {
    const type = CostTypes.find(t => t.key && t.key.toString() === this.type.toString());
    return type ? type.value : null;
  }

  get actionTypeStr() {
    const actionType = ActionTypes.find(at => at.key && at.key.toString() === this.actionType.toString());
    return actionType ? actionType.value : null;
  };
}
