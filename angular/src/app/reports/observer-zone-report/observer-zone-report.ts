import { ObserverZone } from './observer-zone';

export class ObserverZoneReport {
  private _totalFarmerRegisterQuantity = 0;
  private _totalFarmerActualQuantity = 0;
  private _totalRegisterAcreage = 0;
  private _totalActualAcreage = 0;

  observerZones: Array<ObserverZone> = [];

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

  parse(data: Array<any>) {
    if (Array.isArray(data)) {
      data.forEach(
        value => {
          const observerZone = new ObserverZone();
          observerZone.observerName = value.observerName;

          observerZone.parseCompanyData(value.zoneObserverBranches);
          this.observerZones.push(observerZone);
          this._totalFarmerRegisterQuantity += observerZone.totalFarmerRegisterQuantity;
          this._totalFarmerActualQuantity += observerZone.totalFarmerActualQuantity;
          this._totalRegisterAcreage += observerZone.totalRegisterAcreage;
          this._totalActualAcreage += observerZone.totalActualAcreage;
        }
      );
    }
  }
}
