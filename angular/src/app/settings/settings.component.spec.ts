import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MdCardModule, MdInputModule, MdSelectModule, MdToolbarModule } from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { Observable } from 'rxjs/Observable';

import { AuthenticationService } from '../core/services/authentication.service';
import { NotificationService } from '../core/services/notification.service';
import { SettingsComponent } from './settings.component';
import { SettingService } from './setting.service';
import { User } from '../core/models/user';

class AuthenticationServiceClass {
}

describe('SettingsComponent', () => {
  let component: SettingsComponent;
  let fixture: ComponentFixture<SettingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SettingsComponent ],
      imports: [
        MdCardModule,
        MdInputModule,
        MdSelectModule,
        MdToolbarModule,
        NoopAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule
      ],
      providers: [
        { provide: AuthenticationService, useClass: AuthenticationServiceClass },
        { provide: NotificationService, useClass: AuthenticationServiceClass },
      ]
    })
    .overrideComponent(SettingsComponent, {
      set: {
        providers: [
          { provide: SettingService, useValue: {
            getUserInfo: () => Observable.of(new User())
          } }
        ]
      }
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
