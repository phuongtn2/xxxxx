import { BaseModel } from '../../core/models/base-model';

export class DryingOven extends BaseModel {
  id: number;
  registrationId: number;
  length: number;
  width: number;
  height: number;
  purlins: number;
  useHusk: number;
  useNaturalFirewood: number;
  useGrownFirewood: number;
  useCoal: number;
  useOther: number;
  quantity: number;
  delFlag: number;

  get ovenFuel() {
    const fuels = [];
    if (this.useNaturalFirewood) {
      fuels.push('Củi rừng tự nhiên');
    }
    if (this.useGrownFirewood) {
      fuels.push('Củi từ rừng trồng');
    }
    if (this.useHusk) {
      fuels.push('Trấu');
    }
    if (this.useCoal) {
      fuels.push('Than đá');
    }
    if (this.useOther) {
      fuels.push('Khác');
    }

    return fuels.join(', ');
  }
}
