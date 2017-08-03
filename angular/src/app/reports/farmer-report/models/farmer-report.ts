import { Company } from './company';

export class FarmerReport {
  private _totalFarmerQuantity = 0;
  private _totalRegisterAcreage = 0;

  companies: Array<Company> = [];

  get totalFarmerQuantity() {
    return this._totalFarmerQuantity;
  }

  get totalRegisterAcreage(): number {
    return this._totalRegisterAcreage;
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
          this._totalFarmerQuantity += currentCompany.totalFarmerQuantity;
          this._totalRegisterAcreage += currentCompany.totalRegisterAcreage;
        }
      );
    }
  }
}
