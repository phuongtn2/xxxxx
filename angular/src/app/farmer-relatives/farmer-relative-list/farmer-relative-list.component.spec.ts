import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { FarmerRelativeListComponent } from './farmer-relative-list.component';
import { NotificationService } from '../../core/services/notification.service';
import { FarmerRelativeService } from '../farmer-relative.service';
import { Farmer } from '../../core/models/farmer';
import { DialogService } from '../../core/services/dialog.service';

describe('FarmerRelativeListComponent', () => {
  let component: FarmerRelativeListComponent;
  let fixture: ComponentFixture<FarmerRelativeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FarmerRelativeListComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        MdDataTableModule
      ],
      providers: [
        DialogService,
        NotificationService,
        { provide: FarmerRelativeService, useValue: {} }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FarmerRelativeListComponent);
    component = fixture.componentInstance;
    component.farmer = new Farmer();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
