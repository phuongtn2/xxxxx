import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule, MdDialogRef } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { FarmerRelativeRegisterDialogComponent } from './farmer-relative-register-dialog.component';
import { NotificationService } from '../../core/services/notification.service';
import { FarmerRelativeService } from '../farmer-relative.service';

describe('FarmerRelativeRegisterDialogComponent', () => {
  let component: FarmerRelativeRegisterDialogComponent;
  let fixture: ComponentFixture<FarmerRelativeRegisterDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FarmerRelativeRegisterDialogComponent ],
      imports: [
        MaterialModule,
        NoopAnimationsModule,
        ReactiveFormsModule
      ],
      providers: [
        { provide: MdDialogRef, useValue: {} },
        { provide: FarmerRelativeService, useValue: {} },
        { provide: NotificationService, useValue: {} },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FarmerRelativeRegisterDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
