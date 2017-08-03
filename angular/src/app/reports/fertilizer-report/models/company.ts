import { CompanyBranch } from './company-branch';
import { MaterialData } from './material-data';

export class Company {
  private _nPerHa;
  private _p2o5PerHa;
  private _k2oPerHa;
  id: number;
  name: string;
  companyBranches: Array<CompanyBranch> = [];
  acreage = 0;
  materialQuantities = [];

  get nPerHa() {
    if (!this._nPerHa) {
      this._nPerHa = ((this.materialQuantities[0].quantity * 18) +
        (this.materialQuantities[2].quantity * 21) +
        (this.materialQuantities[3].quantity * 34) +
        this.materialQuantities[4].quantity +
        (this.materialQuantities[5].quantity * 5.8) +
        (this.materialQuantities[6].quantity * 7) +
        (this.materialQuantities[7].quantity * 10) +
        (this.materialQuantities[8].quantity * 12) +
        (this.materialQuantities[9].quantity * 12) +
        (this.materialQuantities[10].quantity * 13)) / 100 / this.acreage;
    }
    return this._nPerHa;
  }

  get p2o5PerHa() {
    if (!this._p2o5PerHa) {
      this._p2o5PerHa = ((this.materialQuantities[0].quantity * 46) +
        (this.materialQuantities[5].quantity * 7.5) +
        (this.materialQuantities[6].quantity * 9) +
        (this.materialQuantities[7].quantity * 10) +
        (this.materialQuantities[8].quantity * 11) +
        (this.materialQuantities[9].quantity * 12)) / 100 / this.acreage;
    }
    return this._p2o5PerHa;
  }

  get k2oPerHa() {
    if (!this._k2oPerHa) {
      this._k2oPerHa = ((this.materialQuantities[1].quantity * 50) +
        (this.materialQuantities[5].quantity * 13.5) +
        (this.materialQuantities[6].quantity * 14) +
        (this.materialQuantities[7].quantity * 26) +
        (this.materialQuantities[8].quantity * 18) +
        (this.materialQuantities[9].quantity * 17) +
        (this.materialQuantities[10].quantity * 46)) / 100 / this.acreage;
    }
    return this._k2oPerHa;
  }

  parseBranches(companyBranches: any[]) {
    if (Array.isArray(companyBranches)) {
      companyBranches.forEach(
        (value) => {
          const branch = new CompanyBranch();
          branch.districtName = value['districtName'];
          branch.provinceName = value['provinceName'];
          branch.acreage = value['acreage'];
          branch.density = value['density'];
          branch.parseListMaterial(value['fertilizerReports']);

          if (!this.materialQuantities.length) {
            branch.materials.forEach(
              materialData => {
                this.materialQuantities.push(Object.assign(new MaterialData(), materialData));
              }
            );
          } else if (this.materialQuantities.length !== branch.materials.length) {
            throw new Error();
          } else {
            const r = branch.materials.every(
              (materialData, index) => {
                if (this.materialQuantities[index].id !== materialData.id) {
                  return false;
                }
                this.materialQuantities[index].quantity += materialData.quantity;
                return true;
              }
            );
            if (!r) {
              throw new Error();
            }
          }
          this.acreage += branch.acreage;

          this.companyBranches.push(branch);
        }
      );
    }
  }
}
