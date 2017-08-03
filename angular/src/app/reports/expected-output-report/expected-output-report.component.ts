import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { ExpectedOutputReportService } from './expected-output-report.service';
import { ExpectedOutputReport } from './models/expected-output-report';

@Component({
  selector: 'app-expected-output-report',
  templateUrl: './expected-output-report.component.html',
  styleUrls: [
    '../report-data-table.scss',
    './expected-output-report.component.scss'
  ],
  providers: [ExpectedOutputReportService]
})
export class ExpectedOutputReportComponent implements OnInit {
  title: string;
  data = new ExpectedOutputReport();

  constructor(
    private titleService: Title,
    private expectedOutputReport: ExpectedOutputReportService
  ) {
    this.title = 'Báo cáo dự trù năng xuất';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.expectedOutputReport.getData()
      .subscribe(
        (data) => {
          this.data.parse(data);
        }
      );
  }

  exportReport() {
    this.expectedOutputReport.exportReport();
  }
}
