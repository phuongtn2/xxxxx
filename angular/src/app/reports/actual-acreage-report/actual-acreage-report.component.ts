import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { ActualAcreageReportService } from './actual-acreage-report.service';
import { ActualAcreageReport } from './models/actual-acreage-report';

@Component({
  selector: 'app-actual-acreage-report',
  templateUrl: './actual-acreage-report.component.html',
  styleUrls: [
    '../report-data-table.scss',
    './actual-acreage-report.component.scss'
  ],
  providers: [ActualAcreageReportService]
})
export class ActualAcreageReportComponent implements OnInit {
  title: string;
  data = new ActualAcreageReport();

  constructor(
    private titleService: Title,
    private actualAcreageReport: ActualAcreageReportService
  ) {
    this.title = 'Báo cáo cập nhật diện tích';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.actualAcreageReport.getData()
      .subscribe(
        (data) => {
          this.data.parse(data);
        }
      );
  }

  exportReport() {
    this.actualAcreageReport.exportReport();
  }
}
