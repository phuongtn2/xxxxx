import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/zip';

import { FertilizerReport } from './models/fertilizer-report';
import { FertilizerReportService } from './fertilizer-report.service';
import { MaterialService } from '../../materials/material.service';

@Component({
  selector: 'app-fertilizer-report',
  templateUrl: './fertilizer-report.component.html',
  styleUrls: [
    '../report-data-table.scss',
    './fertilizer-report.component.scss'
  ],
  providers: [MaterialService, FertilizerReportService]
})
export class FertilizerReportComponent implements OnInit {
  title: string;
  data = new FertilizerReport();

  constructor(
    private titleService: Title,
    private materialService: MaterialService,
    private fertilizerReportService: FertilizerReportService
  ) {
    this.title = 'Báo cáo mật độ - phân bón';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    Observable.zip(
      this.fertilizerReportService.getData(),
      this.materialService.getAll(),
      (data, materials) => {
        this.data.parse(data, materials);
      }).subscribe();
  }

  exportReport() {
    this.fertilizerReportService.exportReport();
  }
}
