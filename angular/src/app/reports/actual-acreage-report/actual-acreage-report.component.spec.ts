import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { ActualAcreageReportComponent } from './actual-acreage-report.component';
import { ActualAcreageReportService } from './actual-acreage-report.service';

describe('ActualAcreageReportComponent', () => {
  let component: ActualAcreageReportComponent;
  let fixture: ComponentFixture<ActualAcreageReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActualAcreageReportComponent ],
      imports: [
        FlexLayoutModule,
        MaterialModule
      ]
    })
    .overrideComponent(ActualAcreageReportComponent, {
      set: {
        providers: [
          { provide: ActualAcreageReportService, useValue: {
            getData: () => Observable.of([])
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActualAcreageReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
