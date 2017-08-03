import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

import { Farmer } from '../../core/models/farmer';
import { User } from '../../core/models/user';
import { LocationService } from '../../core/services/location.service';
import { Registration } from '../../crop-session/registration';
import { FieldPlot } from '../field-plot';
import { FieldService } from '../field.service';
import { CropSessionService } from '../../crop-session/crop-session.service';

@Component({
  selector: 'app-plot-detail',
  templateUrl: './plot-detail.component.html',
  styleUrls: ['./plot-detail.component.scss'],
  providers: [CropSessionService, LocationService]
})
export class PlotDetailComponent implements OnInit {
  title: string;
  registration: Registration;
  farmer: Farmer;
  responsibilityEmployee: User;
  plot: FieldPlot;

  constructor(
    private titleService: Title,
    private location: Location,
    private fieldService: FieldService,
    private route: ActivatedRoute,
    private cropSessionService: CropSessionService
  ) {
    this.title = 'Chi Tiết Thửa';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    const farmerCode = this.route.snapshot.params['farmerCode'];
    this.cropSessionService.getRegistrationByFarmerCode(farmerCode)
      .subscribe(
        (registration: Registration) => {
          this.registration = registration;
          this.farmer = registration.farmer;
          this.responsibilityEmployee = registration.responsibilityEmployee;
          this.getFieldDetail();
        }
      );
  }

  goBack() {
    this.location.back();
  }

  getFieldDetail() {
    this.fieldService.getField(this.registration.id, this.registration.cultivationId).subscribe(
      field => {
        const plotId = +this.route.snapshot.params['plotId'];
        this.plot = field.fieldPlots && field.fieldPlots.find(plot => plot.id === plotId);
      }
    );
  }
}
