import { Company } from './company';
import { SeedData } from './seed-data';

export class SeedReport {
  private _parsing = true;
  headers;
  companies: Company[] = [];
  totalActualAcreage = 0;
  seedAcreages = [];

  parse(data, seeds) {
    if (Array.isArray(data)) {
      data.forEach(
        value => {
          const company = new Company();
          company.id = value['companyId'];
          company.name = value['companyName'];
          company.parseBranches(value['seedTypeCompanyBranches']);

          if (!this.headers) {
            this.headers = [];
            company.seedAcreages.forEach(
              seedData => {
                const seed = seeds.find(s => s.id === seedData.seedId);
                if (!seed) {
                  throw new Error('Missing data.');
                }
                this.headers.push({id: seedData.seedId, title: seed.name});
                this.seedAcreages.push(Object.assign(new SeedData(), seedData));
              }
            );
          } else {
            const r = company.seedAcreages.every(
              (seedData, index) => {
                if (this.seedAcreages[index].seedId !== seedData.seedId) {
                  return false;
                }
                this.seedAcreages[index].actualAcreage += seedData.actualAcreage;
                return true;
              }
            );
            if (!r) {
              throw new Error('Invalid data.');
            }
          }
          this.totalActualAcreage += company.totalActualAcreage;

          this.companies.push(company);
        }
      );
      this._parsing = false;
    }
  }
}
