export class MaterialSubType {
  static Seed = new MaterialSubType('seed');

  static Pesticide = new MaterialSubType('pesticide');

  static Fertilizer = new MaterialSubType('fertilizer');

  constructor(public value: string) { }

  toString() { return this.value; }
}
