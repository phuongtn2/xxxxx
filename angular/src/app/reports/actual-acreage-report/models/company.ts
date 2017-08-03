import { CompanyBranch } from './company-branch';

export class Company {
  private _totalFarmerQuantity = 0;
  private _totalActualAcreage = 0;

  id: number;
  name: string;
  companyBranches: Array<CompanyBranch> = [];

  get totalFarmerQuantity() {
    return this._totalFarmerQuantity;
  }

  get totalActualAcreage(): number {
    return this._totalActualAcreage;
  }

  parseBranches(companyBranches: Array<any>) {
    if (Array.isArray(companyBranches)) {
      companyBranches.forEach(
        (value) => {
          const branch = new CompanyBranch();
          branch.districtName = value.districtName;
          branch.provinceName = value.provinceName;
          branch.farmerQuantity = value.farmerQuantity;
          branch.actualAcreage = value.actualAcreage;

          this.companyBranches.push(branch);
          this._totalFarmerQuantity += branch.farmerQuantity;
          this._totalActualAcreage += branch.actualAcreage;
        }
      );
    }
  }
}
