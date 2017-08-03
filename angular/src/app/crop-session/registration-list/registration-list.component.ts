import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { CropSessionService } from '../crop-session.service';
import { Registration } from '../registration';

@Component({
  selector: 'app-registration-list',
  templateUrl: './registration-list.component.html',
  styleUrls: ['./registration-list.component.scss'],
  providers: [CropSessionService]
})
export class RegistrationListComponent implements OnInit {
  title: string;
  registrations: Registration[];
  limit = 10;

  constructor(
    private titleService: Title,
    private cropSessionService: CropSessionService
  ) {
    this.title = 'Danh sách đăng ký';
  }

  ngOnInit() {
    this.titleService.setTitle(this.title);
    this.getRegistrations();
  }

  private getRegistrations() {
    this.cropSessionService.syncRegistrationList()
      .subscribe(
        (registrations) => {
          this.registrations = registrations;
        }
      );
  }
}
