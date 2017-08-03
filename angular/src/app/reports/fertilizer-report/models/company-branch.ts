import { MaterialData } from './material-data';

export class CompanyBranch {
  private _nPerHa;
  private _p2o5PerHa;
  private _k2oPerHa;
  districtName: string;
  provinceName: string;
  acreage: number;
  density: number;
  materials: MaterialData[] = [];

  get nPerHa() {
    if (!this._nPerHa) {
      this._nPerHa = ((this.materials[0].quantity * 18) +
        (this.materials[2].quantity * 21) +
        (this.materials[3].quantity * 34) +
        this.materials[4].quantity +
        (this.materials[5].quantity * 5.8) +
        (this.materials[6].quantity * 7) +
        (this.materials[7].quantity * 10) +
        (this.materials[8].quantity * 12) +
        (this.materials[9].quantity * 12) +
        (this.materials[10].quantity * 13)) / 100 / this.acreage;
    }
    return this._nPerHa;
  }

  get p2o5PerHa() {
    if (!this._p2o5PerHa) {
      this._p2o5PerHa = ((this.materials[0].quantity * 46) +
        (this.materials[5].quantity * 7.5) +
        (this.materials[6].quantity * 9) +
        (this.materials[7].quantity * 10) +
        (this.materials[8].quantity * 11) +
        (this.materials[9].quantity * 12)) / 100 / this.acreage;
    }
    return this._p2o5PerHa;
  }

  get k2oPerHa() {
    if (!this._k2oPerHa) {
      this._k2oPerHa = ((this.materials[1].quantity * 50) +
        (this.materials[5].quantity * 13.5) +
        (this.materials[6].quantity * 14) +
        (this.materials[7].quantity * 26) +
        (this.materials[8].quantity * 18) +
        (this.materials[9].quantity * 17) +
        (this.materials[10].quantity * 46)) / 100 / this.acreage;
    }
    return this._k2oPerHa;
  }

  get nPerTree() {
    return this.nPerHa / this.density * 1000;
  }

  get p2o5PerTree() {
    return this.p2o5PerHa / this.density * 1000;
  }

  get k2oPerTree() {
    return this.k2oPerHa / this.density * 1000;
  }

  parseListMaterial(data) {
    if (Array.isArray(data) && data.length > 10) {
      data.forEach(
        value => {
          const materialData = new MaterialData();
          materialData.id = value['materialId'];
          materialData.quantity = value['materialQuantity'];

          this.materials.push(materialData);
        }
      );
    } else {
      throw new Error('Invalid data.');
    }
  }
}
