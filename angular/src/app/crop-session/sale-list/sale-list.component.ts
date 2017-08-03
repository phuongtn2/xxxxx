import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';

import { Registration } from '../registration';
import { SaleRegister } from '../sale-register';
import { SaleService } from './sale.service';

@Component({
  selector: 'app-sale-list',
  templateUrl: './sale-list.component.html',
  styleUrls: ['./sale-list.component.scss'],
  providers: [SaleService]
})
export class SaleListComponent implements OnChanges, OnInit {
  @Input() registration: Registration;
  saleRegistrations: SaleRegister[];

  constructor(
    private saleService: SaleService
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.registration && changes.registration.currentValue) {
      this.saleService.getList(this.registration.id)
        .subscribe(
          (sales: SaleRegister[]) => {
            this.saleRegistrations = sales;
          }
        );
    }
  }

  ngOnInit() {
  }
}
