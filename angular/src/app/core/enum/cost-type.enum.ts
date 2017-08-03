export class CostType {
  static LaborCost = new CostType('LABOR_COST');

  static Material = new CostType('MATERIAL');

  static Other = new CostType('OTHER');

  constructor(public value: string) { }

  toString() { return this.value; }
}

export const CostTypes = [
  {key: CostType.LaborCost, value: 'Công Lao Động'},
  {key: CostType.Material, value: 'Vật Tư'},
  {key: CostType.Other, value: 'Chi Phí Khác'}
];

export class ActionType {
  static Cultivation = new ActionType('CULTIVATION');

  static Nursery = new ActionType('NURSERY');

  static Harvest = new ActionType('HARVEST');

  static Other = new ActionType('OTHER');

  constructor(public value: string) { }

  toString() { return this.value; }
}

export const ActionTypes = [
  {key: ActionType.Nursery, value: 'Vườn Ươm'},
  {key: ActionType.Cultivation, value: 'Ruộng Trồng'},
  {key: ActionType.Harvest, value: 'Thu Hoạch Và Sấy'}
];
