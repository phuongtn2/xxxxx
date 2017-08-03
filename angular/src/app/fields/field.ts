import { BaseModel } from '../core/models/base-model';
import { Harvest } from './harvest';
import { FieldPlot } from './field-plot';

export class Field extends BaseModel {
  clientPrimaryKey: number;
  id: number;
  registrationId: number;
  seedId: number;
  acreage: number;
  address: string;
  provinceId: number;
  districtId: number;
  wardId: number;
  densityRow: number;
  densityColumn: number;
  plantAcreageRatio: number;
  leafsRatio: number;
  fieldPlots: FieldPlot[];
  harvests: Harvest[];

  get fullAddress() {
    return `${this.wardId} - ${this.districtId} - ${this.provinceId}`;
  }

  get realAcreage() {
    return !this.fieldPlots
      ? Number.NaN
      : this.fieldPlots.reduce(
        (prevValue, currentPlot) => {
          return prevValue + currentPlot.acreage;
        }, 0);
  }

  get totalPlant() {
    return !this.fieldPlots
      ? Number.NaN
      : this.fieldPlots.reduce(
        (prevValue, currentPlot) => {
          return prevValue + (currentPlot.plantRowRatio * currentPlot.rowPlotRatio);
        }, 0);
  }

  // Trung bình số lá /cây
  get averageLeaf() {
    return !this.fieldPlots
      ? Number.NaN
      : this.fieldPlots.reduce(
        (prevValue, currentPlot) => {
          return prevValue + currentPlot.leafsRatio;
        }, 0);
  }

  get totalLeaf() {
    return this.averageLeaf * this.totalPlant;
  }

  // Trung bình số lá sấy /Kg
  // TODO find another name
  get averageMass() {
    return !this.harvests
      ? Number.NaN
      : this.harvests.reduce(
        (prevValue, currentHarvest) => {
          return prevValue + currentHarvest.massRatio;
        }, 0) / this.harvests.length;
  }

  get quantity() {
    return this.totalLeaf / this.averageMass;
  }

  get productivity() {
    return this.quantity / this.realAcreage;
  }

  static assign(data) {
    const obj = super.assign(data);
    if (Array.isArray(obj.fieldPlots)) {
      obj.fieldPlots = obj.fieldPlots.map(value => FieldPlot.assign(value));
    }
    return obj;
  }
}
