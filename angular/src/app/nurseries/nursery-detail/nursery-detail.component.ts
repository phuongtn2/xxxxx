import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { MdDialog } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

import { LocationService } from '../../core/services/location.service';
import { Registration } from '../../crop-session/registration';
import { Material } from '../../materials/material';
import { MapDialogComponent } from '../../location/map-dialog/map-dialog.component';
import { MaterialService } from '../../materials/material.service';
import { Nursery } from '../nursery';
import { CropSessionService } from '../../crop-session/crop-session.service';
import { NurseryService } from '../nursery.service';

@Component({
  selector: 'app-nursery-detail',
  templateUrl: './nursery-detail.component.html',
  styleUrls: ['./nursery-detail.component.scss'],
  providers: [CropSessionService, MaterialService, LocationService]
})
export class NurseryDetailComponent implements OnInit {
  title: string;
  registration: Registration;
  seed: Material;

  private _address: string[] = new Array(4);
  private _nursery: Nursery;

  get address() {
    return this._address.filter(v => v).join(' - ');
  }

  get nursery() {
    return this._nursery;
  }

  set nursery(value) {
    this._nursery = value;
    this.getSeed(this._nursery.seedId);
    this.getAddress(this._nursery.provinceId, this._nursery.districtId, this._nursery.wardId);
  }

  constructor(
    private titleService: Title,
    private dialog: MdDialog,
    private location: Location,
    private route: ActivatedRoute,
    private locationService: LocationService,
    private nurseryService: NurseryService,
    private materialService: MaterialService,
    private cropSessionService: CropSessionService
  ) {
    this.title = 'Chi Tiết Vườn Ươm';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    const farmerCode = this.route.snapshot.params['farmerCode'];
    this.cropSessionService.getRegistrationByFarmerCode(farmerCode)
      .subscribe(
        (registration: Registration) => {
          this.registration = registration;
          this.getNursery();
        }
      );
  }

  goBack() {
    this.location.back();
  }

  openMap(address) {
    const dialogRef = this.dialog.open(MapDialogComponent);
    dialogRef.componentInstance.location = address;
  }

  private getAddress(provinceId: number, districtId: number, wardId: number) {
    this._address = new Array(4);
    this._address[0] = this._nursery.address;
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

  private getNursery() {
    this.nurseryService.getNursery(this.registration.id, this.registration.nurseryId)
      .subscribe(
        (nursery: Nursery) => {
          this.nursery = nursery;
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
}
