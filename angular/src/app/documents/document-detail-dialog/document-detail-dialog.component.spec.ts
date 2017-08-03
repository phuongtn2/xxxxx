import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { MaterialModule, MdDialogRef } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { DocumentDetailDialogComponent } from './document-detail-dialog.component';
import { DocumentService } from '../document.service';
import { NotificationService } from '../../core/services/notification.service';

describe('DocumentDetailDialogComponent', () => {
  let component: DocumentDetailDialogComponent;
  let fixture: ComponentFixture<DocumentDetailDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentDetailDialogComponent ],
      imports: [
        FormsModule,
        MaterialModule,
        NoopAnimationsModule
      ],
      providers: [
        { provide: MdDialogRef, useValue: {} },
        { provide: DocumentService, useValue: {} },
        { provide: NotificationService, useValue: {} }
    ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
