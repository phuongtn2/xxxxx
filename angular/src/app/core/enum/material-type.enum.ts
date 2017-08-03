export class MaterialType {
  static NonEquipment = new MaterialType('non-equipment');

  static Equipment = new MaterialType('equipment');

  constructor(public value: string) { }

  toString() { return this.value; }
}
