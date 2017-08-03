import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

import { Registration } from '../../crop-session/registration';
import { FieldPlot } from '../field-plot';
import { FieldService } from '../field.service';
import { CropSessionService } from '../../crop-session/crop-session.service';

@Component({
  selector: 'app-field-plot-timeline',
  templateUrl: './field-plot-timeline.component.html',
  styleUrls: ['./field-plot-timeline.component.scss'],
  providers: [
    CropSessionService
  ]
})
export class FieldPlotTimelineComponent implements OnInit {
  title: string;
  private registration: Registration;
  orderedTimeline;
  private plot: FieldPlot;
  private plotId: number;

  constructor(
    private titleService: Title,
    private route: ActivatedRoute,
    private location: Location,
    private cropSessionService: CropSessionService,
    private fieldService: FieldService
  ) {
    this.title = 'Timeline';
  }

  ngOnInit() {
    const farmerCode = this.route.snapshot.params['farmerCode'];
    this.plotId = +this.route.snapshot.params['plotId'];
    this.titleService.setTitle(this.title);

    this.cropSessionService.getRegistrationByFarmerCode(farmerCode)
      .subscribe(
        (registration: Registration) => {
          this.registration = registration;
          this.getFieldDetail();
        }
      );
  }

  private getFieldDetail() {
    this.fieldService.getField(this.registration.id, this.registration.cultivationId).subscribe(
      field => {
        const plot = field.fieldPlots && field.fieldPlots.find(p => p.id === this.plotId);
        this.sortData(plot);
      }
    );
  }

  private sortData(data) {
    const orderList = [];
    const indexArray = [];
    if (data != null) {
      for (const item of data.details) {
        const actionUpdateDate = item.actionUpdateDate;
        if (indexArray.indexOf(item.actionUpdateDate) < 0) {
          indexArray.push(actionUpdateDate);
        }
        const indexKey = indexArray.indexOf(actionUpdateDate);
        if (typeof orderList[indexKey] === 'undefined') {
          orderList[indexKey] = [];
        }
        orderList[indexKey].push(item);
      }
    }
    this.orderedTimeline = orderList;
  }

  goBack() {
    this.location.back();
  }
}
