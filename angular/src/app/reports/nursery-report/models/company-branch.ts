export class CompanyBranch {
  districtName: string;
  provinceName: string;
  nurseryStandardQuantity: number;
  get nurseryAcreage(): number {
    return this.nurseryStandardQuantity / 24000;
  }
}
