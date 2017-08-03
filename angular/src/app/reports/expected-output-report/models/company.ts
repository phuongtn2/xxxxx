import { CompanyBranch } from './company-branch';

export class Company {
  private _totalActualAcreage = 0;
  private _totalAverageDensity = 0;
  private _totalAverageLeavesInTree = 0;
  private _totalAverageDriedLeavesInTree = 0;

  id: number;
  name: string;
  companyBranches: Array<CompanyBranch> = [];

  get totalActualAcreage(): number {
    return this._totalActualAcreage;
  }
  get averageDensity(): number {
    return this._totalAverageDensity / this.companyBranches.length;
  }
  get averageLeavesInTree(): number {
    return this._totalAverageLeavesInTree / this.companyBranches.length;
  }
  get averageDriedLeavesInTree(): number {
    return this._totalAverageDriedLeavesInTree / this.companyBranches.length;
  }
  get productivity(): number {
    return (this.totalActualAcreage * this.averageDensity * this.averageLeavesInTree) / this.averageDriedLeavesInTree;
  }
  get output(): number {
    return this.productivity * this.totalActualAcreage;
  }

  parseBranches(companyBranches: Array<any>) {
    if (Array.isArray(companyBranches)) {
      companyBranches.forEach(
        (value) => {
          const branch = new CompanyBranch();
          branch.districtName = value.districtName;
          branch.provinceName = value.provinceName;
          branch.actualAcreage = value.actualAcreage;
          branch.averageDensity = value.averageDensity / value.cultivationQuantity;
          branch.averageLeavesInTree = value.averageLeaveInTree / value.cultivationQuantity;
          branch.averageDriedLeavesInTree = value.averageDriedLeaveInTree / value.cultivationQuantity;

          this.companyBranches.push(branch);
          this._totalActualAcreage += branch.actualAcreage;
          this._totalAverageDensity += branch.averageDensity;
          this._totalAverageLeavesInTree += branch.averageLeavesInTree;
          this._totalAverageDriedLeavesInTree += branch.averageDriedLeavesInTree;
        }
      );
    }
  }
}
