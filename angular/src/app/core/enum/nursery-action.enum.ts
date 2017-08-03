export class NurseryAction {
  static LeafCutting = new NurseryAction('LEAF_CUTTING');

  static Pest = new NurseryAction('PEST');

  static PesticidesSpraying = new NurseryAction('PESTICIDES_SPRAYING');

  constructor(public value: string) { }

  toString() { return this.value; }
}

const a = [];
a.push('Leaf Cutting');
a.push('Pest');
a.push('Pesticides spraying');
// a[NurseryAction.Pest.value] = 'PEST';
// a[NurseryAction.PesticidesSpraying.value] = 'PESTICIDES_SPRAYING';
export const NurseryActions = a;
