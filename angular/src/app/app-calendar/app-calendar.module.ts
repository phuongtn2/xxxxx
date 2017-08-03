import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/components/button/button';
import { InputTextModule } from 'primeng/components/inputtext/inputtext';

import { CalendarComponent } from './calendar/calendar.component';

@NgModule({
  imports: [
    CommonModule,
    ButtonModule,
    InputTextModule
  ],
  declarations: [CalendarComponent],
  exports: [
    CalendarComponent
  ]
})
export class AppCalendarModule { }
