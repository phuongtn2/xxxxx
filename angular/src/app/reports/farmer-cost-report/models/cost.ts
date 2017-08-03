import { Farmer } from './farmer';
import { FarmerCost } from './farmer-cost';

export class Cost {
  title: string;
  farmers: Farmer[] = [];

  costs: FarmerCost[] = [];

  get averageQuantity() {
    const totalQty = this.costs.reduce(
      (prevValue, currentValue) => {
        return prevValue + currentValue.quantityPerHa;
      }, 0);
    if (!totalQty) {
      return totalQty;
    }
    return totalQty / this.farmers.length;
  }

  get averagePrice() {
    const totalPrice = this.costs.reduce(
      (prevValue, currentValue) => {
        return prevValue + currentValue.unitPrice;
      }, 0);
    if (!totalPrice) {
      return totalPrice;
    }
    return totalPrice / this.farmers.length;
  }

  get totalPrice() {
    return this.averageQuantity * this.averagePrice;
  }
}
