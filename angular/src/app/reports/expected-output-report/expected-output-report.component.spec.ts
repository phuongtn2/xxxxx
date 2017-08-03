import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { ExpectedOutputReportComponent } from './expected-output-report.component';
import { ExpectedOutputReportService } from './expected-output-report.service';

describe('ExpectedOutputReportComponent', () => {
  let component: ExpectedOutputReportComponent;
  let fixture: ComponentFixture<ExpectedOutputReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExpectedOutputReportComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule
      ]
    })
    .overrideComponent(ExpectedOutputReportComponent, {
      set: {
        providers: [
          { provide: ExpectedOutputReportService, useValue: {
            getData: () => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpectedOutputReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
