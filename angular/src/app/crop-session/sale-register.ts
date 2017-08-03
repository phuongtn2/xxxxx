import { BaseModel } from '../core/models/base-model';

export class SaleRegister extends BaseModel {
  id: number;
  saleIndex: number;
  registrationId: number;
  proposerId: number;
  expectedQuantity: number;
  status: number;
  dateRegistration: number;
}
