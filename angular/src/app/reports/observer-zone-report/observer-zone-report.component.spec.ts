import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { ObserverZoneReportComponent } from './observer-zone-report.component';
import { ObserverZoneReportService } from './observer-zone-report.service';

describe('ObserverZoneReportComponent', () => {
  let component: ObserverZoneReportComponent;
  let fixture: ComponentFixture<ObserverZoneReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ObserverZoneReportComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule
      ]
    })
    .overrideComponent(ObserverZoneReportComponent, {
      set: {
        providers: [
          { provide: ObserverZoneReportService, useValue: {
            getData: () => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ObserverZoneReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
