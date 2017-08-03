import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/zip';

import { Registration } from '../registration';
import { CropMaterial } from './crop-material';
import { MaterialService } from '../../materials/material.service';

@Component({
  selector: 'app-crop-material-list',
  templateUrl: './crop-material-list.component.html',
  styleUrls: ['./crop-material-list.component.scss']
})
export class CropMaterialListComponent implements OnChanges {
  cropMaterials: CropMaterial[];
  @Input() registration: Registration;

  constructor(
    private materialService: MaterialService
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.registration && changes.registration.currentValue) {
      const cropMaterialObs = Observable.of(this.registration.cropMaterials);
      const materialObs = this.materialService.getAll();
      Observable.zip(
        cropMaterialObs,
        materialObs,
        (cropMaterials, materials) => {
          return cropMaterials.map(
            cropMaterial => {
              cropMaterial.material = materials.find(material => material.id === cropMaterial.materialId);
              return cropMaterial;
            }
          );
        }
      ).subscribe(cropMaterials => this.cropMaterials = cropMaterials);
    }
  }
}
