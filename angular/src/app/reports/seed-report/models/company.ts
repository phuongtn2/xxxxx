import { CompanyBranch } from './company-branch';
import { SeedData } from './seed-data';

export class Company {
  id: number;
  name: string;
  companyBranches: Array<CompanyBranch> = [];
  totalActualAcreage = 0;
  seedAcreages: SeedData[] = [];

  parseBranches(companyBranches: any[]) {
    if (Array.isArray(companyBranches)) {
      companyBranches.forEach(
        (value) => {
          const branch = new CompanyBranch();
          branch.districtName = value['districtName'];
          branch.provinceName = value['provinceName'];
          branch.parseListSeed(value['seedReports']);

          if (!this.seedAcreages.length) {
            branch.seedAcreages.forEach(
              seedData => {
                this.seedAcreages.push(Object.assign(new SeedData(), seedData));
              }
            );
          } else if (this.seedAcreages.length !== branch.seedAcreages.length) {
            throw new Error();
          } else {
            const r = branch.seedAcreages.every(
              (seedData, index) => {
                if (this.seedAcreages[index].seedId !== seedData.seedId) {
                  return false;
                }
                this.seedAcreages[index].actualAcreage += seedData.actualAcreage;
                return true;
              }
            );
            if (!r) {
              throw new Error();
            }
          }
          this.totalActualAcreage += branch.totalActualAcreage;

          this.companyBranches.push(branch);
        }
      );
    }
  }
}
