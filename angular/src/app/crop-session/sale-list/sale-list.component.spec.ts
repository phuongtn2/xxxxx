import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { SaleListComponent } from './sale-list.component';
import { SaleService } from './sale.service';

describe('SaleListComponent', () => {
  let component: SaleListComponent;
  let fixture: ComponentFixture<SaleListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SaleListComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule,
        MdDataTableModule
      ]
    })
    .overrideComponent(SaleListComponent, {
      set: {
        providers: [
          { provide: SaleService, useValue: {} }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SaleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
