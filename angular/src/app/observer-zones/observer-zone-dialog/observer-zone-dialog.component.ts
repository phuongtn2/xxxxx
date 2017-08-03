import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Response } from '@angular/http';
import { MdDialogRef } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/zip';

import { Zone } from '../../core/models/zone';
import { LocationService } from '../../core/services/location.service';
import { ZoneService } from '../../core/services/zone.service';
import { ObserverZone } from '../../core/models/observer-zone';
import { NotificationService } from '../../core/services/notification.service';
import { Company } from '../../core/models/company';
import { CompanyService } from '../../core/services/company.service';

@Component({
  selector: 'app-observer-zone-dialog',
  templateUrl: './observer-zone-dialog.component.html',
  styleUrls: ['./observer-zone-dialog.component.scss'],
  providers: [
    ZoneService,
    LocationService,
    CompanyService
  ]
})
export class ObserverZoneDialogComponent implements OnInit {
  title: string;
  observerForm: FormGroup;
  isEditing = false;
  zone: Zone;
  zones: Zone[];
  observerZone: ObserverZone;
  employeeId: number;
  companies: Array<Company>;
  companyZones: Array<Zone>;
  observerZones: Array<ObserverZone>;

  constructor(
    private titleService: Title,
    private dialogRef: MdDialogRef<ObserverZoneDialogComponent>,
    private fb: FormBuilder,
    private zoneService: ZoneService,
    private locationService: LocationService,
    private companyService: CompanyService,
    private notification: NotificationService
  ) {
    this.title = 'Chọn vùng giám sát';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.getZones();
    this.buildForm();
    if (this.observerZone) {
      this.setFormData();
    }

  }

  private setFormData() {
    this.observerForm.setValue({
      id: this.observerZone.id,
      observerZoneId: this.observerZone.cultivationZoneId,
      companyId: this.observerZone.companyId,
      employeeId: this.employeeId
    });
    this.isEditing = true;
    // this.observerForm.get('observerZone').disable();
  }

  private prepareRegisterData(): ObserverZone {
    const formModel = this.observerForm.value;
    const obZ = new ObserverZone();
    obZ.id = formModel.id;
    obZ.cultivationZoneId = formModel.observerZoneId;
    obZ.companyId = formModel.companyId;
    obZ.employeeId = this.employeeId;
    return obZ;
  }

  saveObserverZone() {
    this.observerZone = this.prepareRegisterData();
    this.zoneService.saveZone(this.observerZone)
      .catch(
      (response: Response) => {
        this.notification.notify('Có lỗi xảy ra.');
        return Observable.throw(response);
      }
    )
      .subscribe(
        () => {
          this.notification.notify(`Đã lưu thông tin XXX`);
          this.dialogRef.close(true);
        }
      );
  }

  getZones() {
    const ob1 = this.locationService.getProvinces();
    const ob2 = this.locationService.getAllDistricts();
    const ob3 = this.zoneService.getZones();
    const companyObservable = this.companyService.getCompanies();

    Observable.zip(ob1, ob2, ob3, companyObservable, (provinces, districts, zones: Zone[], companies) => {
      this.companies = companies;
      zones = zones.filter(
        zone => {
          const hasInObserverList = this.observerZones.find(obZone => obZone.cultivationZoneId === zone.id);
          let hasEditedObZone = false;
          if (this.observerZone) {
            hasEditedObZone = this.observerZone.cultivationZoneId === zone.id;
          }
        return hasEditedObZone || !hasInObserverList;
        }
      );

      zones.forEach((zone) => {
        zone.province = provinces.find(p => p.id === zone.provinceId);
        zone.district = districts.find(d => d.id === zone.districtId);
      });
      return zones;
    }).subscribe(
      zones => {
        this.zones = zones;
      }
    );
  }

  onSelectCompany(companyId) {
    this.companyZones = this.zones.filter(zone => zone.companyId === companyId);
  }

  private buildForm() {
    this.observerForm = this.fb.group({
      id: [''],
      observerZoneId: ['', Validators.required],
      companyId: ['', Validators.required],
      employeeId: ['', Validators.required]
    });
  }
}
