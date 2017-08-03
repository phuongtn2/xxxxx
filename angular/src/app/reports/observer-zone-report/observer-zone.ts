import { Company } from './company';

export class ObserverZone {
  private _totalFarmerRegisterQuantity = 0;
  private _totalFarmerActualQuantity = 0;
  private _totalRegisterAcreage = 0;
  private _totalActualAcreage = 0;

  observerName: string;
  companies: Array<Company> = [];

  get totalFarmerRegisterQuantity(): number {
    return this._totalFarmerRegisterQuantity;
  }
  get totalFarmerActualQuantity(): number {
    return this._totalFarmerActualQuantity;
  }
  get totalRegisterAcreage(): number {
    return this._totalRegisterAcreage;
  }
  get totalActualAcreage(): number {
    return this._totalActualAcreage;
  }

  parseCompanyData(zoneObserverBranches: Array<any>) {
    if (Array.isArray(zoneObserverBranches)) {
      zoneObserverBranches.forEach(
        value => {
          const company = new Company();
          company.name = value.companyName;
          company.farmerRegisterQuantity = value.farmerRegistQuantity;
          company.farmerActualQuantity = value.farmerActualQuantity;
          company.registerAcreage = value.registAcreage;
          company.actualAcreage = value.actualAcreage;

          this.companies.push(company);
          this._totalFarmerRegisterQuantity += company.farmerRegisterQuantity;
          this._totalFarmerActualQuantity += company.farmerActualQuantity;
          this._totalRegisterAcreage += company.registerAcreage;
          this._totalActualAcreage += company.actualAcreage;
        }
      );
    }
  }
}
