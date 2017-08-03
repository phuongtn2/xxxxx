import { Company } from './company';

export class ExpectedOutputReport {
  private _totalActualAcreage = 0;

  companies: Array<Company> = [];

  get totalActualAcreage(): number {
    return this._totalActualAcreage;
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
          this._totalActualAcreage += currentCompany.totalActualAcreage;
        }
      );
    }
  }
}
