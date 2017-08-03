import { ActionType, CostType } from '../../../core/enum/cost-type.enum';
import { Technician } from './technician';
import { Farmer } from './farmer';
import { Cost } from './cost';
import { FarmerCost } from './farmer-cost';
import { ActionGroup } from './action-group';
import { CostGroup } from './cost-group';

export class FarmerCostReport {

  technicians: Technician[] = [];
  sections: {[id: string]: CostGroup} = {};
  get totalPrice() {
    let total = 0;
    for (const groupId of Object.keys(this.sections)) {
      total += this.sections[groupId].totalPrice;
    }
    return total;
  }

  constructor() {
    let arr = new CostGroup();
    arr.groups[ActionType.Nursery.value] = new ActionGroup();
    arr.groups[ActionType.Cultivation.value] = new ActionGroup();
    arr.groups[ActionType.Harvest.value] = new ActionGroup();
    this.sections[CostType.LaborCost.value] = arr;

    arr = new CostGroup();
    arr.groups[ActionType.Nursery.value] = new ActionGroup();
    arr.groups[ActionType.Cultivation.value] = new ActionGroup();
    arr.groups[ActionType.Harvest.value] = new ActionGroup();
    this.sections[CostType.Material.value] = arr;

    arr = new CostGroup();
    arr.groups['OTHER'] = new ActionGroup();
    this.sections[CostType.Other.value] = arr;
  }

  parse(data) {
    if (Array.isArray(data)) {
      data.forEach(
        value => {
          const actionGroup = this.sections[value['type']].groups[value['actionType']];
          let currentCost = actionGroup.find(cost => cost.title === value['title']);
          if (!currentCost) {
            currentCost = new Cost();
            currentCost.title = value['title'];
            actionGroup.push(currentCost);
          }
          this.parseActionDetail(currentCost, value['actionDetailReports']);
        }
      );
    }
  }

  parseActionDetail(action, detail) {
    if (Array.isArray(detail)) {
      detail.forEach(
        value => {
          let technician = this.technicians.find(t => t.id === value['employeeId']);
          if (!technician) {
            technician = new Technician();
            technician.id = value['employeeId'];
            technician.name = value['employeeName'];
            this.technicians.push(technician);
          }

          let farmer = technician.farmers.find(f => f.id === value['farmerId']);
          if (!farmer) {
            farmer = new Farmer();
            farmer.id = value['farmerId'];
            farmer.name = value['farmerName'];
            farmer.actualAcreage = value['acreage'];
            technician.farmers.push(farmer);
          }
          action.farmers.push(farmer);

          const farmerCost = new FarmerCost();
          farmerCost.farmerId = farmer.id;
          farmerCost.farmer = farmer;
          farmerCost.quantity = value['quantity'];
          farmerCost.unitPrice = value['price'];
          action.costs[farmer.id] = farmerCost;
        }
      );
    }
  }
}
