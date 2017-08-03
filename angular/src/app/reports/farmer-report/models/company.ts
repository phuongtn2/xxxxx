import { CompanyBranch } from './company-branch';

export class Company {
  private _totalFarmerQuantity = 0;
  private _totalRegisterAcreage = 0;

  id: number;
  name: string;
  companyBranches: Array<CompanyBranch> = [];

  get totalFarmerQuantity() {
    return this._totalFarmerQuantity;
  }

  get totalRegisterAcreage(): number {
    return this._totalRegisterAcreage;
  }

  parseBranches(companyBranches: Array<any>) {
    if (Array.isArray(companyBranches)) {
      companyBranches.forEach(
        (value) => {
          const branch = new CompanyBranch();
          branch.districtName = value.districtName;
          branch.provinceName = value.provinceName;
          branch.farmerQuantity = value.farmerQuantity;
          branch.registerAcreage = value.registAcreage;

          this.companyBranches.push(branch);
          this._totalFarmerQuantity += branch.farmerQuantity;
          this._totalRegisterAcreage += branch.registerAcreage;
        }
      );
    }
  }
}
