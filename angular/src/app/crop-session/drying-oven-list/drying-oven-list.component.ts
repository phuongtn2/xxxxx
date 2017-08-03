import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

import { Registration } from '../registration';
import { DryingOven } from './drying-oven';

@Component({
  selector: 'app-drying-oven-list',
  templateUrl: './drying-oven-list.component.html',
  styleUrls: ['./drying-oven-list.component.scss']
})
export class DryingOvenListComponent implements OnChanges {
  dryingOvens: DryingOven[];
  @Input() registration: Registration;

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.registration && changes.registration.currentValue) {
      this.dryingOvens = this.registration.dryingOvens;
    }
  }
}
