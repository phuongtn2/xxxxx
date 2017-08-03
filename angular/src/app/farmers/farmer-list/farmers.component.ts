import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { Farmer } from '../../core/models/farmer';
import { FarmerService } from '../../core/services/farmer.service';

@Component({
  selector: 'app-farmers',
  templateUrl: './farmers.component.html',
  styleUrls: ['./farmers.component.scss'],
  providers: [FarmerService]
})
export class FarmersComponent implements OnInit {
  title: string;
  farmers: Farmer[];
  limit = 10;

  constructor(
    private titleService: Title,
    private farmerService: FarmerService
  ) {
    this.title = 'Danh sách nông dân';
  }

  ngOnInit() {
    this.setTitle();
    this.getFarmers();
  }

  private getFarmers() {
    this.farmerService.getAll()
      .subscribe(
        (farmers) => {
          this.farmers = farmers;
        }
      );
  }

  private setTitle() {
    this.titleService.setTitle(this.title);
  }
}
