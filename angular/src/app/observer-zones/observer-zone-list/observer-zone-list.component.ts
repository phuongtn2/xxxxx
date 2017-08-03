import { Component, Input, OnInit } from '@angular/core';
import { MdDialog, MdDialogConfig } from '@angular/material';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/zip';

import { ObserverZoneDialogComponent } from '../observer-zone-dialog/observer-zone-dialog.component';
import { LocationService } from '../../core/services/location.service';
import { ObserverService } from '../../core/services/observer.service';
import { ZoneService } from '../../core/services/zone.service';
import { ObserverZone } from '../../core/models/observer-zone';
import { Zone } from '../../core/models/zone';
import { CompanyService } from '../../core/services/company.service';

@Component({
  selector: 'app-observer-zone-list',
  templateUrl: './observer-zone-list.component.html',
  styleUrls: ['./observer-zone-list.component.scss'],
  providers: [
    CompanyService,
    ZoneService
  ]
})
export class ObserverZoneListComponent implements OnInit {

  observerZones;

  @Input() user;

  constructor(
    private dialog: MdDialog,
    private locationService: LocationService,
    private companyService: CompanyService,
    private zoneService: ZoneService,
    private observerService: ObserverService
  ) { }

  ngOnInit() {
    this.getZones();
  }

  getZones() {
    const ob1 = this.locationService.getProvinces();
    const ob2 = this.locationService.getAllDistricts();
    const ob3 = this.observerService.getByEmployeeId(this.user.id);
    const ob4 = this.zoneService.getZones();
    const companyObservable = this.companyService.getCompanies();

    Observable.zip(
      ob1, ob2, ob3, ob4, companyObservable,
      (provinces, districts, observers: ObserverZone[], zones: Zone[], companies) => {
        zones.forEach((zone) => {
          zone.province = provinces.find(p => p.id === zone.provinceId);
          zone.district = districts.find(d => d.id === zone.districtId);
        });
        observers.forEach((observer) => {
          observer.zone = zones.find(zone => zone.id === observer.cultivationZoneId);
          observer.company = companies.find(company => company.id === observer.companyId);
        });
        return observers;
      }
    ).subscribe(
      observers => {
        this.observerZones = observers;
      });
  }

  openDialog(observerZone?) {
    const config = new MdDialogConfig();
    config.width = '400px';
    const dialogRef = this.dialog.open(ObserverZoneDialogComponent, config);
    dialogRef.componentInstance.employeeId = this.user.id;
    dialogRef.componentInstance.observerZones = this.observerZones;
    if (observerZone) {
      dialogRef.componentInstance.observerZone = observerZone;
      dialogRef.componentInstance.isEditing = true;
    }
    dialogRef.afterClosed().subscribe(
      (result) => {
        if (result) {
          this.getZones();
        }
      }
    );
  }
}
