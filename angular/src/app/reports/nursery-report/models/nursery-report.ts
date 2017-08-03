import { Company } from './company';

export class NurseryReport {
  private _totalNurseryStandardQuantity = 0;
  private _totalNurseryAcreage = 0;

  companies: Array<Company> = [];

  get totalNurseryStandardQuantity() {
    return this._totalNurseryStandardQuantity;
  }

  get totalNurseryAcreage(): number {
    return this._totalNurseryAcreage;
  }

  parse(data: Array<any>) {
    if (Array.isArray(data)) {
      data.forEach(
        (value) => {
          const currentCompany = new Company();
          currentCompany.id = value.companyId;
          currentCompany.name = value.companyName;
          currentCompany.parseBranches(value.companyBranches);

          this.companies.push(currentCompany);
          this._totalNurseryStandardQuantity += currentCompany.totalNurseryStandardQuantity;
          this._totalNurseryAcreage += currentCompany.totalNurseryAcreage;
        }
      );
    }
  }
}
