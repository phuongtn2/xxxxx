import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

import { Registration } from '../../crop-session/registration';
import { Nursery } from '../nursery';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { NurseryService } from '../nursery.service';

@Component({
  selector: 'app-nursery-timeline',
  templateUrl: './nursery-timeline.component.html',
  styleUrls: ['./nursery-timeline.component.scss'],
  providers: [CropSessionService]
})
export class NurseryTimelineComponent implements OnInit {
  title: string;
  private registration: Registration;
  orderedTimeline;
  private nursery: Nursery;

  constructor(
    private titleService: Title,
    private route: ActivatedRoute,
    private location: Location,
    private cropSessionService: CropSessionService,
    private nurseryService: NurseryService
  ) {
    this.title = 'Timeline';
  }

  ngOnInit() {
    const farmerCode = this.route.snapshot.params['farmerCode'];
    this.titleService.setTitle(this.title);

    this.cropSessionService.getRegistrationByFarmerCode(farmerCode)
      .subscribe(
        (registration: Registration) => {
          this.registration = registration;
          this.getNursery();
        }
      );
  }

  private getNursery() {
    this.nurseryService.getNursery(this.registration.id, this.registration.nurseryId)
      .subscribe(
        (nursery: Nursery) => {
          this.sortData(nursery);
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
