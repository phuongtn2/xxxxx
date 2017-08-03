import { CompanyBranch } from './company-branch';

export class Company {
  private _totalNurseryStandardQuantity = 0;
  private _totalNurseryAcreage = 0;

  id: number;
  name: string;
  companyBranches: Array<CompanyBranch> = [];

  get totalNurseryStandardQuantity(): number {
    return this._totalNurseryStandardQuantity;
  }
  get totalNurseryAcreage() {
    return this._totalNurseryAcreage;
  }

  parseBranches(companyBranches: Array<any>) {
    if (Array.isArray(companyBranches)) {
      companyBranches.forEach(
        (value) => {
          const branch = new CompanyBranch();
          branch.districtName = value.districtName;
          branch.provinceName = value.provinceName;
          branch.nurseryStandardQuantity = value.nurseryStandardQuantity;

          this.companyBranches.push(branch);
          this._totalNurseryStandardQuantity += branch.nurseryStandardQuantity;
          this._totalNurseryAcreage += branch.nurseryAcreage;
        }
      );
    }
  }
}
