import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MdCardModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';
import { Observable } from 'rxjs/Observable';

import { CropMaterialListComponent } from './crop-material-list.component';
import { MaterialService } from '../../materials/material.service';

describe('CropMaterialListComponent', () => {
  let component: CropMaterialListComponent;
  let fixture: ComponentFixture<CropMaterialListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CropMaterialListComponent ],
      imports: [
        FlexLayoutModule,
        MdCardModule,
        MdDataTableModule
      ],
      providers: [
        { provide: MaterialService, useValue: {
          getAll: () => Observable.of([])
        } }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CropMaterialListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
