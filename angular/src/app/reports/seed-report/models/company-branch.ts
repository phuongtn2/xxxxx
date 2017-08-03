import { SeedData } from './seed-data';

export class CompanyBranch {
  districtName: string;
  provinceName: string;
  totalActualAcreage = 0;
  seedAcreages: SeedData[] = [];

  parseListSeed(data) {
    if (Array.isArray(data)) {
      data.forEach(
        value => {
          const seedData = new SeedData();
          seedData.seedId = value['seedId'];
          seedData.actualAcreage = value['actualAcreage'];
          this.totalActualAcreage += seedData.actualAcreage;

          this.seedAcreages.push(seedData);
        }
      );
    }
  }
}
