export class CompanyBranch {
  districtName: string;
  provinceName: string;
  actualAcreage: number;
  averageDensity: number;
  averageLeavesInTree: number;
  averageDriedLeavesInTree: number;

  get productivity(): number {
    return (this.actualAcreage * this.averageDensity * this.averageLeavesInTree) / this.averageDriedLeavesInTree;
  }

  get output(): number {
    return this.productivity * this.actualAcreage;
  }
}
