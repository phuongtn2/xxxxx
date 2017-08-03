import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MdAutocompleteModule, MdDialogRef, MdIconModule, MdInputModule } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Observable } from 'rxjs/Observable';

import { MaterialDetailDialogComponent } from './material-detail-dialog.component';
import { MaterialService } from '../../materials/material.service';
import { NotificationService } from '../../core/services/notification.service';
import { CompanyMaterialService } from '../company-material.service';
import { HttpService } from '../../core/services/http.service';

const materialServiceStub = {
  getAll: () => Observable.of([])
};
const notification = {};
const companyMaterialService = {};

describe('MaterialDetailDialogComponent', () => {
  let component: MaterialDetailDialogComponent;
  let fixture: ComponentFixture<MaterialDetailDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MaterialDetailDialogComponent ],
      imports: [
        // TODO: check this
        NoopAnimationsModule,
        FlexLayoutModule,
        MdAutocompleteModule,
        MdIconModule,
        MdInputModule,
        ReactiveFormsModule
      ],
      providers: [
        { provide: MdDialogRef, useValue: {} },
        { provide: HttpService, useValue: {} },
        { provide: NotificationService, useValue: notification }
      ]
    })
    .overrideComponent(MaterialDetailDialogComponent, {
      set: {
        providers: [
          { provide: MaterialService, useValue: materialServiceStub },
          { provide: CompanyMaterialService, useValue: companyMaterialService }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MaterialDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
