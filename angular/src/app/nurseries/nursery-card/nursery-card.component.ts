import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MdDialog, MdDialogConfig } from '@angular/material';

import { LocationService } from '../../core/services/location.service';
import { Registration } from '../../crop-session/registration';
import { MaterialService } from '../../materials/material.service';
import { Nursery } from '../nursery';
import { NurseryService } from '../nursery.service';
import { NurseryDetailDialogComponent } from '../nursery-detail-dialog/nursery-detail-dialog.component';

@Component({
  selector: 'app-nursery-card',
  templateUrl: './nursery-card.component.html',
  styleUrls: ['./nursery-card.component.scss'],
  providers: [MaterialService]
})
export class NurseryCardComponent implements OnChanges, OnInit {
  private _nurseryInfo: Nursery;
  _address: string[] = new Array(4);

  get address() {
    return this._address.filter(v => v).join(' - ');
  }


  get nurseryInfo() {
    return this._nurseryInfo;
  }

  set nurseryInfo(value) {
    this._nurseryInfo = value;
    this.getSeed(value.seedId);
    this.getAddress(this._nurseryInfo.provinceId, this._nurseryInfo.districtId, this._nurseryInfo.wardId);
  }

  @Input() registration: Registration;
  seed;

  constructor(
    private dialog: MdDialog,
    private locationService: LocationService,
    private nurseryService: NurseryService,
    private materialService: MaterialService
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.registration && changes.registration.currentValue) {
      if (this.registration.nurseryId) {
        this.nurseryService.getNursery(this.registration.id, this.registration.nurseryId)
          .subscribe(
            (nursery) => {
              this.nurseryInfo = nursery;
            }
          );
      }
    }
  }

  ngOnInit() {
  }

  openDialog() {
    const config = new MdDialogConfig();
    config.width = '600px';
    const dialogRef = this.dialog.open(NurseryDetailDialogComponent, config);
    dialogRef.componentInstance.registrationId = this.registration.id;
    dialogRef.componentInstance.nurseryInfo = Nursery.assign(this.nurseryInfo);
    dialogRef.afterClosed().subscribe(
      (newNursery) => {
        if (newNursery) {
          this.nurseryInfo = newNursery;
        }
      }
    );
  }

  private getAddress(provinceId: number, districtId: number, wardId: number) {
    this._address = new Array(4);
    this._address[0] = this._nurseryInfo.address;
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

  private getSeed(seedId: number) {
    this.materialService.get(seedId)
      .subscribe(
        (material) => {
          this.seed = material;
        }
      );
  }
}
