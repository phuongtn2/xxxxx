import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

import { LocationService } from '../../core/services/location.service';
import { ZoneService } from '../../core/services/zone.service';
import { District } from '../../core/models/location/district';
import { Province } from '../../core/models/location/province';
import { Ward } from '../../core/models/location/ward';
import { Zone } from '../../core/models/zone';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.scss'],
  providers: [LocationService, ZoneService]
})
export class LocationComponent implements OnChanges {
  allProvinces: Province[];
  allDistricts: District[];
  wards: Ward[];

  private zones: Zone[];

  @Input() formGroup;
  @Input() provinces;
  @Input() districts;
  @Input() restrictedByZone = false;

  constructor(
    private locationService: LocationService,
    private zoneService: ZoneService
  ) { }

  ngOnChanges(changes: SimpleChanges) {console.log(changes);
    if (changes.formGroup && changes.formGroup.currentValue) {
      const provinceId = this.formGroup.get('provinceId').value;
      if (provinceId) {
        this.locationService.getDistricts(this.formGroup.get('provinceId').value)
          .subscribe(
            districts => {
              this.allDistricts = districts;
              if (this.districts) {
                this.allDistricts = this.allDistricts.filter(district => this.districts.has(district.id));
              }
            }
          );
      }
      const districtId = this.formGroup.get('districtId').value;
      if (districtId) {
        this.locationService.getWards(districtId)
          .subscribe(
            wards => {
              this.wards = wards;
            }
          );
      }
    }

    // check restricted zone location list
    if (changes.restrictedByZone && changes.restrictedByZone.currentValue && this.restrictedByZone) {
      this.getCompanyZones().subscribe(() => this.getProvinces());
    } else {
      this.getProvinces();
    }
  }

  getProvinces() {
    this.locationService.getProvinces().subscribe(
      provinces => {
        this.allProvinces = provinces;
        if (this.provinces) {
          this.allProvinces = this.allProvinces.filter(province => this.provinces.has(province.id));
        }
      }
    );
  }

  onSelectProvince(provinceId) {
    this.formGroup.patchValue({
      districtId: '',
      wardId: ''
    });
    return this.locationService.getDistricts(provinceId)
      .subscribe(
        districts => {
          this.allDistricts = districts;
          if (this.districts) {
            this.allDistricts = this.allDistricts.filter(district => this.districts.has(district.id));
          }
        }
      );
  }

  onSelectDistrict(districtId) {
    this.formGroup.patchValue({
      wardId: ''
    });
    this.locationService.getWards(districtId)
      .subscribe(
        wards => {
          this.wards = wards;
        }
      );
  }

  /**
   * Get company zones only.
   */
  private getCompanyZones() {
    if (this.zones) { // already get list of company zone
      return;
    }
    return this.zoneService.getZones().map(
      zones => {
        this.zones = zones;
        this.provinces = new Set(this.zones.map(zone => zone.provinceId));
        this.districts = new Set(this.zones.map(zone => zone.districtId));
      }
    );
  }
}
