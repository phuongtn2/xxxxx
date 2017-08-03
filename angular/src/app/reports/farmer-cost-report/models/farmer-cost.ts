import { Farmer } from './farmer';

export class FarmerCost {
  farmerId: number;
  farmer: Farmer;
  quantity: number;
  get quantityPerHa() {
    if (!this.farmer.actualAcreage) {
      return 0;
    }
    return this.quantity / this.farmer.actualAcreage;
  }
  unitPrice: number;
}
