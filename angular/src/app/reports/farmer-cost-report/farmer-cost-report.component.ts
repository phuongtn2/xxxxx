import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { ActionTypes, CostType, CostTypes } from '../../core/enum/cost-type.enum';
import { FarmerCostReport } from './models/farmer-cost-report';
import { FarmerCostReportService } from './farmer-cost-report.service';

@Component({
  selector: 'app-farmer-cost-report',
  templateUrl: './farmer-cost-report.component.html',
  styleUrls: [
    '../report-data-table.scss',
    './farmer-cost-report.component.scss'
  ],
  providers: [FarmerCostReportService]
})
export class FarmerCostReportComponent implements OnInit {
  title: string;
  data = new FarmerCostReport();

  get CostTypes() { return CostTypes; }

  getActionTypes(costType: {key: CostType, value: string}) {
    if (costType.key.toString() !== CostType.Other.toString()) {
      return ActionTypes;
    }
    return [costType];
  }

  constructor(
    private titleService: Title,
    private farmerCostReportService: FarmerCostReportService
  ) {
    this.title = 'Báo cáo chi phí nông dân';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.farmerCostReportService.getData(7)
      .subscribe(
        (data) => {
          this.data.parse(data);
        }
      );
  }

  exportReport() {
    this.farmerCostReportService.exportReport(7);
  }
}
