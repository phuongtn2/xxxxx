import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { NurseryReportService } from './nursery-report.service';
import { NurseryReport } from './models/nursery-report';

@Component({
  selector: 'app-nursery-report',
  templateUrl: './nursery-report.component.html',
  styleUrls: [
    '../report-data-table.scss',
    './nursery-report.component.scss'
  ],
  providers: [NurseryReportService]
})
export class NurseryReportComponent implements OnInit {
  title: string;
  data = new NurseryReport();

  constructor(
    private titleService: Title,
    private nurseryReport: NurseryReportService
  ) {
    this.title = 'Báo cáo cập nhật diện tích';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.nurseryReport.getData()
      .subscribe(
        (data) => {
          this.data.parse(data);
        }
      );
  }

  exportReport() {
    this.nurseryReport.exportReport();
  }
}
