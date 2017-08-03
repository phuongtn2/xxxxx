import { ActionGroup } from './action-group';

export class CostGroup {
  groups: {[id: string]: ActionGroup} = {};

  get totalPrice() {
    let total = 0;
    for (const groupId of Object.keys(this.groups)) {
      total += this.groups[groupId].totalPrice;
    }
    return total;
  }
}
