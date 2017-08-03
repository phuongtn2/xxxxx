import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MdCardModule, MdDialogModule, MdIconModule, MdToolbarModule } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MdDataTableModule } from '@dtrthi/md-data-table';
import { Observable } from 'rxjs/Observable';

import { MaterialsComponent } from './materials.component';
import { DialogService } from '../../core/services/dialog.service';
import { CompanyMaterialService } from '../company-material.service';

describe('MaterialsComponent', () => {
  let component: MaterialsComponent;
  let fixture: ComponentFixture<MaterialsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MaterialsComponent ],
      imports: [
        MdCardModule,
        MdDataTableModule,
        MdDialogModule,
        MdIconModule,
        MdToolbarModule,
        NoopAnimationsModule
      ],
      providers: [
        DialogService
      ]
    })
    .overrideComponent(MaterialsComponent, {
      set: {
        providers: [
          { provide: CompanyMaterialService, useValue: {
            getAll: () => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MaterialsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
