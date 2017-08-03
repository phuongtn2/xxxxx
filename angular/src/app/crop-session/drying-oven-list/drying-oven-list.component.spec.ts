import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MdCardModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { DryingOvenListComponent } from './drying-oven-list.component';

describe('DryingOvenListComponent', () => {
  let component: DryingOvenListComponent;
  let fixture: ComponentFixture<DryingOvenListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DryingOvenListComponent ],
      imports: [
        FlexLayoutModule,
        MdCardModule,
        MdDataTableModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DryingOvenListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
