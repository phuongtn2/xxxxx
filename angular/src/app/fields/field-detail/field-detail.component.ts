import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { MdDialog } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

import { Farmer } from '../../core/models/farmer';
import { User } from '../../core/models/user';
import { LocationService } from '../../core/services/location.service';
import { Registration } from '../../crop-session/registration';
import { Field } from '../field';
import { FieldService } from '../field.service';
import { Harvest } from '../harvest';
import { Material } from '../../materials/material';
import { MaterialService } from '../../materials/material.service';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { MapDialogComponent } from '../../location/map-dialog/map-dialog.component';

@Component({
  selector: 'app-field-detail',
  templateUrl: './field-detail.component.html',
  styleUrls: ['./field-detail.component.scss'],
  providers: [CropSessionService, MaterialService, LocationService]
})
export class FieldDetailComponent implements OnInit {
  title: string;
  private _address: string[] = new Array(4);

  registration: Registration;
  field: Field;
  seed: Material;
  farmer: Farmer;
  responsibilityEmployee: User;
  harvests: Harvest;

  get address() {
    return this._address.filter(v => v).join(' - ');
  }

  constructor(
    private titleService: Title,
    private location: Location,
    private fieldService: FieldService,
    private dialog: MdDialog,
    private route: ActivatedRoute,
    private materialService: MaterialService,
    private cropSessionService: CropSessionService,
    private locationService: LocationService
  ) {
    this.title = 'Chi Tiết Ruộng Trồng';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.field = this.field || new Field();
    this.registration = this.registration || new Registration();
    this.farmer = this.farmer || new Farmer();
    this.responsibilityEmployee = this.responsibilityEmployee || new User();
    this.harvests = this.harvests || new Harvest();
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

  private getSeed(seedId: number) {
    this.materialService.get(seedId)
      .subscribe(
        (material) => {
          this.seed = material;
        }
      );
  }

  getAddress(provinceId: number, districtId: number, wardId: number) {
    this._address = new Array(4);
    this._address[0] = this.field.address;
    this.locationService.getProvince(provinceId).subscribe(
      province => {
        if (province) {
          this._address[3] = province.name;
          this.locationService.getDistrict(districtId, provinceId)
            .subscribe(
              district => {
                if (district) {
                  this._address[2] = district.name;
                  this.locationService.getWard(wardId, districtId)
                    .subscribe(
                      ward => {
                        if (ward) {
                          this._address[1] = ward.name;
                        }
                      }
                    );
                }
              }
            );
        }
      }
    );
  }

  goBack() {
    this.location.back();
  }

  getFieldDetail() {
    this.fieldService.getField(this.registration.id, this.registration.cultivationId).subscribe(
      field => {
        this.field = field;
        // this.harvests = this.field.harvests[0];
        this.getAddress(this.field.provinceId, this.field.districtId, this.field.wardId);
        this.getSeed(this.field.seedId);
      }
    );
  }

  openMap(address) {
    const dialogRef = this.dialog.open(MapDialogComponent);
    dialogRef.componentInstance.location = address;
  }
}
