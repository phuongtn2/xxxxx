import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { ObserverZoneReportService } from './observer-zone-report.service';
import { ObserverZoneReport } from './observer-zone-report';

@Component({
  selector: 'app-observer-zone-report',
  templateUrl: './observer-zone-report.component.html',
  styleUrls: [
    '../report-data-table.scss',
    './observer-zone-report.component.scss'
  ],
  providers: [ObserverZoneReportService]
})
export class ObserverZoneReportComponent implements OnInit {
  title: string;
  data = new ObserverZoneReport();

  constructor(
    private titleService: Title,
    private observerZoneReportService: ObserverZoneReportService
  ) {
    this.title = 'Báo cáo giám sát';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.observerZoneReportService.getData()
      .subscribe(
        (data) => {
          this.data.parse(data);
        }
      );
  }

  exportReport() {
    this.observerZoneReportService.exportReport();
  }
}
