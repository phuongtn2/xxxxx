export class FieldAction {
  static Manuring = new FieldAction('MANURING');

  static Irrigation = new FieldAction('IRRIGATION');

  static PesticidesSpraying = new FieldAction('PESTICIDES_SPRAYING');

  static CloverCutting = new FieldAction('CLOVER_CUTTING');

  static Pest = new FieldAction('PEST');

  constructor(public value: string) { }

  toString() { return this.value; }
}
