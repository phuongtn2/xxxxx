import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { NurseryReportComponent } from './nursery-report.component';
import { NurseryReportService } from './nursery-report.service';

describe('NurseryReportComponent', () => {
  let component: NurseryReportComponent;
  let fixture: ComponentFixture<NurseryReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NurseryReportComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule
      ]
    })
    .overrideComponent(NurseryReportComponent, {
      set: {
        providers: [
          { provide: NurseryReportService, useValue: {
            getData: () => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NurseryReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
