import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/zip';

import { SeedReport } from './models/seed-report';
import { SeedReportService } from './seed-report.service';
import { MaterialService } from '../../materials/material.service';

@Component({
  selector: 'app-seed-report',
  templateUrl: './seed-report.component.html',
  styleUrls: [
    '../report-data-table.scss',
    './seed-report.component.scss'
  ],
  providers: [MaterialService, SeedReportService]
})
export class SeedReportComponent implements OnInit {
  title: string;
  data = new SeedReport();

  constructor(
    private titleService: Title,
    private materialService: MaterialService,
    private seedReportService: SeedReportService
  ) {
    this.title = 'Báo cáo cơ cấu giống';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    Observable.zip(
      this.seedReportService.getData(),
      this.materialService.getSeeds(),
      (data, seeds) => {
        this.data.parse(data, seeds);
      }).subscribe();
  }

  exportReport() {
    this.seedReportService.exportReport();
  }
}
