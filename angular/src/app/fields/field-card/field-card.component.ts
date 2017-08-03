import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MdDialog, MdDialogConfig } from '@angular/material';

import { Registration } from '../../crop-session/registration';
import { LocationService } from '../../core/services/location.service';
import { Field } from '../field';
import { FieldService } from '../field.service';
import { MaterialService } from '../../materials/material.service';
import { FieldDetailDialogComponent } from '../field-detail-dialog/field-detail-dialog.component';

@Component({
  selector: 'app-field-card',
  templateUrl: './field-card.component.html',
  styleUrls: ['./field-card.component.scss'],
  providers: [MaterialService, LocationService]
})
export class FieldCardComponent implements OnChanges, OnInit {
  private _fieldInfo: Field;
  _address: string[] = new Array(4);

  get address() {
    return this._address.filter(v => v).join(' - ');
  }

  get fieldInfo() {
    return this._fieldInfo;
  }

  set fieldInfo(value) {
    this._fieldInfo = value;
    this.getAddress(this._fieldInfo.provinceId, this._fieldInfo.districtId, this._fieldInfo.wardId);
    this.getSeed(value.seedId);
  }

  @Input() registration: Registration;
  seed;

  constructor(
    private dialog: MdDialog,
    private locationService: LocationService,
    private fieldService: FieldService,
    private materialService: MaterialService
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.registration && changes.registration.currentValue) {
      if (this.registration.cultivationId) {
        this.fieldService.getField(this.registration.id, this.registration.cultivationId)
          .subscribe(
            (field) => {
              this.fieldInfo = field;
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
    const dialogRef = this.dialog.open(FieldDetailDialogComponent, config);
    dialogRef.componentInstance.registrationId = this.registration.id;
    dialogRef.componentInstance.fieldInfo = Field.assign(this.fieldInfo);
    dialogRef.afterClosed().subscribe(
      (newField) => {
        if (newField) {
          this.fieldInfo = newField;
        }
      }
    );
  }

  getAddress(provinceId: number, districtId: number, wardId: number) {
    this._address = new Array(4);
    this._address[0] = this._fieldInfo.address;
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
