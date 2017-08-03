import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { FarmerReportService } from './farmer-report.service';
import { FarmerReport } from './models/farmer-report';

@Component({
  selector: 'app-farmer-report',
  templateUrl: './farmer-report.component.html',
  styleUrls: [
    '../report-data-table.scss',
    './farmer-report.component.scss'
  ],
  providers: [FarmerReportService]
})
export class FarmerReportComponent implements OnInit {
  title: string;
  data = new FarmerReport();

  constructor(
    private titleService: Title,
    private farmerReportService: FarmerReportService
  ) {
    this.title = 'Báo cáo nông dân';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.farmerReportService.getData()
      .subscribe(
        (data) => {
          this.data.parse(data);
        }
      );
  }

  exportReport() {
    this.farmerReportService.exportReport();
  }
}
