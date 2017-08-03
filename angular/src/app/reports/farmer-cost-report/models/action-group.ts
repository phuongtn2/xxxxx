import { Cost } from './cost';

export class ActionGroup {
  costs: Cost[] = [];

  get totalPrice() {
    return this.costs.reduce(
      (prevValue, currentValue) => {
        return prevValue + currentValue.totalPrice;
      }, 0);
  }

  find(fnc) {
    return this.costs.find(fnc);
  }

  push(cost) {
    this.costs.push(cost);
  }
}
