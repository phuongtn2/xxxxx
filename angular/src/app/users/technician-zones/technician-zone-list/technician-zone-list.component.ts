import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder } from '@angular/forms';
import { MdCheckboxChange } from '@angular/material';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/zip';

import { LocationService } from '../../../core/services/location.service';
import { ZoneService } from '../../../core/services/zone.service';
import { User } from '../../../core/models/user';
import { Zone } from '../../../core/models/zone';
import { TechnicianZoneService } from '../technician-zone.service';
import { TechnicianZone } from '../technician-zone';

@Component({
  selector: 'app-technician-zone-list',
  templateUrl: './technician-zone-list.component.html',
  styleUrls: ['./technician-zone-list.component.scss'],
  providers: [ZoneService, TechnicianZoneService]
})
export class TechnicianZoneListComponent implements OnInit {

  zones;
  technicianZones: TechnicianZone[] = [];
  formArray: FormArray;
  changing = false;

  @Input() user: User;

  constructor(
    private fb: FormBuilder,
    private locationService: LocationService,
    private zoneService: ZoneService,
    private technicianZoneService: TechnicianZoneService
  ) { }

  ngOnInit() {
    if (!this.user) {
      throw new Error('Must pass a user instance.');
    }

    this.formArray = this.fb.array([]);
    this.getZones();
    this.getTechnicianZones();
  }

  getZones() {
    const ob1 = this.locationService.getProvinces();
    const ob2 = this.locationService.getAllDistricts();
    const ob4 = this.zoneService.getZones();

    Observable.zip(
      ob1, ob2, ob4,
      (provinces, districts, zones: Zone[]) => {
        zones.forEach((zone) => {
          zone.province = provinces.find(p => p.id === zone.provinceId);
          zone.district = districts.find(d => d.id === zone.districtId);
        });
        return zones;
      }
    ).subscribe(
        zones => {
          this.zones = zones;
        }
      );
  }

  isTechnicianZone(zone: Zone) {
    const technicianZone = this.technicianZones.find(tz => tz.cultivationZoneId === zone.id);
    return technicianZone && !technicianZone.delFlag;
  }

  onCheckboxChange(event: MdCheckboxChange, zone: Zone) {
    this.formArray.markAsDirty();
    let groupIndex = null;
    const formGroup = this.formArray.controls.find(
      (control, index) => {
        if (control.value.cultivationZoneId === zone.id) {
          groupIndex = index;
          return true;
        }
        return false;
      }
    );
    if (event.checked) {
      if (!formGroup) {
        const newTechnicianZone = new TechnicianZone();
        newTechnicianZone.employeeId = this.user.id;
        newTechnicianZone.cultivationZoneId = zone.id;
        this.formArray.push(this.fb.group(newTechnicianZone));
      } else if (formGroup.get('id')) {
        formGroup.get('delFlag').setValue(1);
      }
    } else {
      if (formGroup) {
        if (formGroup.get('id')) {
          formGroup.get('delFlag').setValue(1);
        } else {
          this.formArray.removeAt(groupIndex);
        }
      }
    }
  }

  saveTechnicianZones() {
    this.changing = true;
    this.technicianZoneService.sync(this.user.id, this.formArray.value)
      .subscribe(
        () => {
          this.getTechnicianZones();
          this.changing = false;
        }
      );
  }

  private getTechnicianZones() {
    this.technicianZoneService.getByTechnicianId(this.user.id)
      .subscribe(
        (technicianZones: TechnicianZone[]) => {
          this.technicianZones = technicianZones;
          this.formArray = this.fb.array([]);
          this.technicianZones.forEach(
            (technicianZone) => {
              this.formArray.push(this.fb.group(technicianZone));
            }
          );
        }
      );
  }
}
