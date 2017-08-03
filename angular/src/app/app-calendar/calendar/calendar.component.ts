import {
  AfterViewInit,
  Component, ElementRef, EventEmitter, forwardRef, HostBinding, Input, OnDestroy, OnInit, Output, Renderer,
  ViewChild
} from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { AbstractControl, NG_VALUE_ACCESSOR, NG_VALIDATORS } from '@angular/forms';
import { DomHandler } from 'primeng/components/dom/domhandler';

export const CALENDAR_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => CalendarComponent),
  multi: true
};

export const CALENDAR_VALIDATOR: any = {
  provide: NG_VALIDATORS,
  useExisting: forwardRef(() => CalendarComponent),
  multi: true
};

export interface LocaleSettings {
  firstDayOfWeek?: number;
  dayNames: string[];
  dayNamesShort: string[];
  dayNamesMin: string[];
  monthNames: string[];
  monthNamesShort: string[];
}

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: [
    './_calendar-theme.scss',
    './calendar.component.scss'
  ],
  animations: [
    trigger('overlayState', [
      state('hidden', style({
        opacity: 0
      })),
      state('visible', style({
        opacity: 1
      })),
      transition('visible => hidden', animate('400ms ease-in')),
      transition('hidden => visible', animate('400ms ease-out'))
    ])
  ],
  providers: [DomHandler, CALENDAR_VALUE_ACCESSOR, CALENDAR_VALIDATOR]
})
export class CalendarComponent implements OnInit, AfterViewInit, OnDestroy {

  @Input() defaultDate: Date;

  @Input() style: string;

  @Input() styleClass: string;

  @Input() inputStyle: string;

  @Input() inputStyleClass: string;

  @Input() placeholder: string;

  @Input() disabled: any;

  @Input() dateFormat = 'mm/dd/yy';

  @Input() inline = false;

  @Input() showOtherMonths = true;

  @Input() selectOtherMonths: boolean;

  @Input() showIcon: boolean;

  @Input() icon = 'fa-calendar';

  @Input() appendTo: any;

  @Input() readonlyInput: boolean;

  @Input() shortYearCutoff: any = '+10';

  @Input() monthNavigator: boolean;

  @Input() yearNavigator: boolean;

  @Input() yearRange: string;

  @Input() showTime: boolean;

  @Input() hourFormat = '24';

  @Input() timeOnly: boolean;

  @Input() stepHour = 1;

  @Input() stepMinute = 1;

  @Input() stepSecond = 1;

  @Input() showSeconds = false;

  @Input() required: boolean;

  @Input() showOnFocus = true;

  @Input() dataType = 'date';

  @Output() onFocus: EventEmitter<any> = new EventEmitter();

  @Output() onBlur: EventEmitter<any> = new EventEmitter();

  @Output() onSelect: EventEmitter<any> = new EventEmitter();

  @Input() locale: LocaleSettings = {
    firstDayOfWeek: 0,
    dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
    dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
    dayNamesMin: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
    monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
    monthNamesShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
  };

  @Input() tabindex: number;

  @ViewChild('datepicker') overlayViewChild: ElementRef;

  value: Date;

  dates: any[];

  weekDays: string[] = [];

  currentMonthText: string;

  currentMonth: number;

  currentYear: number;

  currentHour: number;

  currentMinute: number;

  currentSecond: number;

  pm: boolean;

  overlay: HTMLDivElement;

  overlayVisible: boolean;

  closeOverlay = true;

  dateClick: boolean;

  calendarElement: any;

  documentClickListener: any;

  ticksTo1970: number;

  yearOptions: number[];

  @HostBinding('class.ui-inputwrapper-focus') focus: boolean;

  @HostBinding('class.ui-inputwrapper-filled') filled: boolean;

  inputFieldValue: string = null;

  _minDate: Date;

  _maxDate: Date;

  _isValid = true;

  onModelChange: (value) => { };

  onModelTouched: () => { };

  @Input() get minDate(): Date {
    return this._minDate;
  }

  set minDate(date: Date) {
    this._minDate = date;
    this.createMonth(this.currentMonth, this.currentYear);
  }

  @Input() get maxDate(): Date {
    return this._maxDate;
  }

  set maxDate(date: Date) {
    this._maxDate = date;
    this.createMonth(this.currentMonth, this.currentYear);
  }

  constructor(
    public el: ElementRef,
    public domHandler: DomHandler,
    public renderer: Renderer
  ) { }

  ngOnInit() {
    const date = this.defaultDate || new Date();
    let dayIndex = this.locale.firstDayOfWeek;
    for (let i = 0; i < 7; i++) {
      this.weekDays.push(this.locale.dayNamesMin[dayIndex]);
      dayIndex = (dayIndex === 6) ? 0 : ++dayIndex;
    }

    this.currentMonth = date.getMonth();
    this.currentYear = date.getFullYear();
    if (this.showTime) {
      this.currentMinute = date.getMinutes();
      this.currentSecond = date.getSeconds();
      this.pm = date.getHours() > 11;

      if (this.hourFormat === '12') {
        this.currentHour = date.getHours() === 0 ? 12 : date.getHours() % 12;
      } else {
        this.currentHour = date.getHours();
      }
    } else if (this.timeOnly) {
      this.currentMinute = 0;
      this.currentHour = 0;
      this.currentSecond = 0;
    }

    this.createMonth(this.currentMonth, this.currentYear);

    this.ticksTo1970 = (((1970 - 1) * 365 + Math.floor(1970 / 4) - Math.floor(1970 / 100) +
    Math.floor(1970 / 400)) * 24 * 60 * 60 * 10000000);

    if (this.yearNavigator && this.yearRange) {
      this.yearOptions = [];
      const years = this.yearRange.split(':'),
        yearStart = parseInt(years[0], 10),
        yearEnd = parseInt(years[1], 10);

      for (let i = yearStart; i <= yearEnd; i++) {
        this.yearOptions.push(i);
      }
    }
  }

  ngAfterViewInit() {
    this.overlay = <HTMLDivElement> this.overlayViewChild.nativeElement;

    if (!this.inline && this.appendTo) {
      if (this.appendTo === 'body') {
        document.body.appendChild(this.overlay);
      } else {
        this.domHandler.appendChild(this.overlay, this.appendTo);
      }
    }
  }

  createMonth(month: number, year: number) {
    this.dates = [];
    this.currentMonth = month;
    this.currentYear = year;
    this.currentMonthText = this.locale.monthNames[month];
    const firstDay = this.getFirstDayOfMonthIndex(month, year);
    const daysLength = this.getDaysCountInMonth(month, year);
    const prevMonthDaysLength = this.getDaysCountInPrevMonth(month, year);
    const sundayIndex = this.getSundayIndex();
    let dayNo = 1;
    const today = new Date();

    for (let i = 0; i < 6; i++) {
      const week = [];

      if (i === 0) {
        for (let j = (prevMonthDaysLength - firstDay + 1); j <= prevMonthDaysLength; j++) {
          const prev = this.getPreviousMonthAndYear(month, year);
          week.push({
            day: j,
            month: prev.month,
            year: prev.year,
            otherMonth: true,
            today: this.isToday(today, j, prev.month, prev.year),
            selectable: this.isSelectable(j, prev.month, prev.year)
          });
        }

        const remainingDaysLength = 7 - week.length;
        for (let j = 0; j < remainingDaysLength; j++) {
          week.push({
            day: dayNo, month: month, year: year, today: this.isToday(today, dayNo, month, year),
            selectable: this.isSelectable(dayNo, month, year)
          });
          dayNo++;
        }
      } else {
        for (let j = 0; j < 7; j++) {
          if (dayNo > daysLength) {
            const next = this.getNextMonthAndYear(month, year);
            week.push({
              day: dayNo - daysLength, month: next.month, year: next.year, otherMonth: true,
              today: this.isToday(today, dayNo - daysLength, next.month, next.year),
              selectable: this.isSelectable((dayNo - daysLength), next.month, next.year)
            });
          } else {
            week.push({
              day: dayNo, month: month, year: year, today: this.isToday(today, dayNo, month, year),
              selectable: this.isSelectable(dayNo, month, year)
            });
          }

          dayNo++;
        }
      }

      this.dates.push(week);
    }
  }

  prevMonth(event) {
    if (this.disabled) {
      event.preventDefault();
      return;
    }

    if (this.currentMonth === 0) {
      this.currentMonth = 11;
      this.currentYear--;
    } else {
      this.currentMonth--;
    }

    this.createMonth(this.currentMonth, this.currentYear);
    event.preventDefault();
  }

  nextMonth(event) {
    if (this.disabled) {
      event.preventDefault();
      return;
    }

    if (this.currentMonth === 11) {
      this.currentMonth = 0;
      this.currentYear++;
    } else {
      this.currentMonth++;
    }

    this.createMonth(this.currentMonth, this.currentYear);
    event.preventDefault();
  }

  onDateSelect(event, dateMeta) {
    if (this.disabled || !dateMeta.selectable) {
      event.preventDefault();
      return;
    }

    if (dateMeta.otherMonth) {
      if (this.selectOtherMonths) {
        this.selectDate(dateMeta);
      }
    } else {
      this.selectDate(dateMeta);
    }

    this.dateClick = true;
    this.updateInputfield();
    event.preventDefault();
  }

  updateInputfield() {
    if (this.value) {
      let formattedValue;

      if (this.timeOnly) {
        formattedValue = this.formatTime(this.value);
      } else {
        formattedValue = this.formatDate(this.value, this.dateFormat);
        if (this.showTime) {
          formattedValue += ' ' + this.formatTime(this.value);
        }
      }

      this.inputFieldValue = formattedValue;
    } else {
      this.inputFieldValue = '';
    }

    this.updateFilledState();
  }

  selectDate(dateMeta) {
    this.value = new Date(dateMeta.year, dateMeta.month, dateMeta.day);
    if (this.showTime) {
      if (this.hourFormat === '12' && this.pm && this.currentHour !== 12) {
        this.value.setHours(this.currentHour + 12);
      } else {
        this.value.setHours(this.currentHour);
      }

      this.value.setMinutes(this.currentMinute);
      this.value.setSeconds(this.currentSecond);
    }
    this._isValid = true;
    this.updateModel();
    this.onSelect.emit(this.value);
  }

  updateModel() {
    if (this.dataType === 'date') {
      this.onModelChange(this.value);
    } else if (this.dataType === 'string') {
      if (this.timeOnly) {
        this.onModelChange(this.formatTime(this.value));
      } else {
        this.onModelChange(this.formatDate(this.value, this.dateFormat));
      }
    }
  }

  getFirstDayOfMonthIndex(month: number, year: number) {
    const day = new Date();
    day.setDate(1);
    day.setMonth(month);
    day.setFullYear(year);

    const dayIndex = day.getDay() + this.getSundayIndex();
    return dayIndex >= 7 ? dayIndex - 7 : dayIndex;
  }

  getDaysCountInMonth(month: number, year: number) {
    return 32 - this.daylightSavingAdjust(new Date(year, month, 32)).getDate();
  }

  getDaysCountInPrevMonth(month: number, year: number) {
    const prev = this.getPreviousMonthAndYear(month, year);
    return this.getDaysCountInMonth(prev.month, prev.year);
  }

  getPreviousMonthAndYear(month: number, year: number) {
    let m, y;

    if (month === 0) {
      m = 11;
      y = year - 1;
    } else {
      m = month - 1;
      y = year;
    }

    return {'month': m, 'year': y};
  }

  getNextMonthAndYear(month: number, year: number) {
    let m, y;

    if (month === 11) {
      m = 0;
      y = year + 1;
    } else {
      m = month + 1;
    }

    return {'month': m, 'year': y};
  }

  getSundayIndex() {
    return this.locale.firstDayOfWeek > 0 ? 7 - this.locale.firstDayOfWeek : 0;
  }

  isSelected(dateMeta): boolean {
    if (this.value) {
      return this.value.getDate() === dateMeta.day &&
        this.value.getMonth() === dateMeta.month &&
        this.value.getFullYear() === dateMeta.year;
    } else {
      return false;
    }
  }

  isToday(today, day, month, year): boolean {
    return today.getDate() === day && today.getMonth() === month && today.getFullYear() === year;
  }

  // TODO custom function
  isTask(date): boolean {
    const d = new Date();
    d.setFullYear(date.year, date.month, date.day);
    d.setHours(0, 0, 0, 0);
    const dayTask = sessionStorage.getItem('dayTask');
    if (dayTask) {
      return dayTask.includes(`${d.getTime()}`);
    } else {
      return false;
    }
  }

  isSelectable(day, month, year): boolean {
    let validMin = true;
    let validMax = true;

    if (this.minDate) {
      if (this.minDate.getFullYear() > year) {
        validMin = false;
      } else if (this.minDate.getFullYear() === year) {
        if (this.minDate.getMonth() > month) {
          validMin = false;
        } else if (this.minDate.getMonth() === month) {
          if (this.minDate.getDate() > day) {
            validMin = false;
          }
        }
      }
    }

    if (this.maxDate) {
      if (this.maxDate.getFullYear() < year) {
        validMax = false;
      } else if (this.maxDate.getFullYear() === year) {
        if (this.maxDate.getMonth() < month) {
          validMax = false;
        } else if (this.maxDate.getMonth() === month) {
          if (this.maxDate.getDate() < day) {
            validMax = false;
          }
        }
      }
    }

    return validMin && validMax;
  }

  onInputFocus(inputfield, event) {
    this.focus = true;
    if (this.showOnFocus) {
      this.showOverlay(inputfield);
    }
    this.onFocus.emit(event);
  }

  onInputBlur(event) {
    this.focus = false;
    this.onBlur.emit(event);
    this.updateInputfield();
    this.onModelTouched();
  }

  onButtonClick(event, inputfield) {
    this.closeOverlay = false;

    if (!this.overlay.offsetParent) {
      inputfield.focus();
      this.showOverlay(inputfield);
    } else {
      this.closeOverlay = true;
    }
  }

  onInputKeydown(event) {
    if (event.keyCode === 9) {
      this.overlayVisible = false;
    }
  }

  onMonthDropdownChange(m: string) {
    this.currentMonth = parseInt(m, 10);
    this.createMonth(this.currentMonth, this.currentYear);
  }

  onYearDropdownChange(y: string) {
    this.currentYear = parseInt(y, 10);
    this.createMonth(this.currentMonth, this.currentYear);
  }

  incrementHour(event) {
    const newHour = this.currentHour + this.stepHour;
    if (this.hourFormat === '24') {
      this.currentHour = (newHour >= 24) ? (newHour - 24) : newHour;
    } else if (this.hourFormat === '12') {
      this.currentHour = (newHour >= 13) ? (newHour - 12) : newHour;
    }

    this.updateTime();

    event.preventDefault();
  }

  decrementHour(event) {
    const newHour = this.currentHour - this.stepHour;
    if (this.hourFormat === '24') {
      this.currentHour = (newHour < 0) ? (24 + newHour) : newHour;
    } else if (this.hourFormat === '12') {
      this.currentHour = (newHour <= 0) ? (12 + newHour) : newHour;
    }

    this.updateTime();

    event.preventDefault();
  }

  incrementMinute(event) {
    const newMinute = this.currentMinute + this.stepMinute;
    this.currentMinute = (newMinute > 59) ? newMinute - 60 : newMinute;

    this.updateTime();

    event.preventDefault();
  }

  decrementMinute(event) {
    const newMinute = this.currentMinute - this.stepMinute;
    this.currentMinute = (newMinute < 0) ? 60 + newMinute : newMinute;

    this.updateTime();

    event.preventDefault();
  }

  incrementSecond(event) {
    const newSecond = this.currentSecond + this.stepSecond;
    this.currentSecond = (newSecond > 59) ? newSecond - 60 : newSecond;

    this.updateTime();

    event.preventDefault();
  }

  decrementSecond(event) {
    const newSecond = this.currentSecond - this.stepSecond;
    this.currentSecond = (newSecond < 0) ? 60 + newSecond : newSecond;

    this.updateTime();

    event.preventDefault();
  }

  updateTime() {
    this.value = this.value || new Date();
    if (this.hourFormat === '12' && this.pm && this.currentHour !== 12) {
      this.value.setHours(this.currentHour + 12);
    } else {
      this.value.setHours(this.currentHour);
    }

    this.value.setMinutes(this.currentMinute);
    this.value.setSeconds(this.currentSecond);
    this.updateModel();
    this.onSelect.emit(this.value);
    this.updateInputfield();
  }

  toggleAMPM(event) {
    this.pm = !this.pm;
    this.updateTime();
    event.preventDefault();
  }

  onInput(event) {
    const val = event.target.value;
    try {
      this.value = this.parseValueFromString(val);
      this.updateUI();
      this._isValid = true;
    } catch (err) {
      // invalid date
      this.value = null;
      this._isValid = false;
    }

    this.updateModel();
    this.filled = val != null && val.length;
  }

  parseValueFromString(text: string): Date {
    let dateValue;
    const parts: string[] = text.split(' ');

    if (this.timeOnly) {
      dateValue = new Date();
      this.populateTime(dateValue, parts[0], parts[1]);
    } else {
      if (this.showTime) {
        dateValue = this.parseDate(parts[0], this.dateFormat);
        this.populateTime(dateValue, parts[1], parts[2]);
      } else {
        dateValue = this.parseDate(text, this.dateFormat);
      }
    }

    return dateValue;
  }

  populateTime(value, timeString, ampm) {
    if (this.hourFormat === '12' && !ampm) {
      throw new Error('Invalid Time');
    }

    this.pm = (ampm === 'PM' || ampm === 'pm');
    const time = this.parseTime(timeString);
    value.setHours(time.hour);
    value.setMinutes(time.minute);
    value.setSeconds(time.second);
  }

  updateUI() {
    const val = this.value || this.defaultDate || new Date();
    this.createMonth(val.getMonth(), val.getFullYear());

    if (this.showTime || this.timeOnly) {
      const hours = val.getHours();

      if (this.hourFormat === '12') {
        if (hours >= 12) {
          this.currentHour = (hours === 12) ? 12 : hours - 12;
        } else {
          this.currentHour = (hours === 0) ? 12 : hours;
        }
      } else {
        this.currentHour = val.getHours();
      }

      this.currentMinute = val.getMinutes();
      this.currentSecond = val.getSeconds();
    }
  }

  onDatePickerClick(event) {
    this.closeOverlay = this.dateClick;
  }

  showOverlay(inputfield) {
    if (this.appendTo) {
      this.domHandler.absolutePosition(this.overlay, inputfield);
    } else {
      this.domHandler.relativePosition(this.overlay, inputfield);
    }

    this.overlayVisible = true;
    this.overlay.style.zIndex = String(++DomHandler.zindex);

    this.bindDocumentClickListener();
  }

  writeValue(value: any): void {
    this.value = value;
    if (this.value && typeof this.value === 'string') {
      this.value = this.parseValueFromString(this.value);
    }

    this.updateInputfield();
    this.updateUI();
  }

  registerOnChange(fn: (value) => { }): void {
    this.onModelChange = fn;
  }

  registerOnTouched(fn: () => { }): void {
    this.onModelTouched = fn;
  }

  setDisabledState(val: boolean): void {
    this.disabled = val;
  }

  // Ported from jquery-ui datepicker formatDate
  formatDate(date, format) {
    if (!date) {
      return '';
    }

    let iFormat,
      output = '',
      literal = false;
    const lookAhead = (match) => {
        const matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) === match);
        if (matches) {
          iFormat++;
        }
        return matches;
      },
      formatNumber = (match, value, len) => {
        let num = '' + value;
        if (lookAhead(match)) {
          while (num.length < len) {
            num = '0' + num;
          }
        }
        return num;
      },
      formatName = (match, value, shortNames, longNames) => {
        return (lookAhead(match) ? longNames[value] : shortNames[value]);
      };

    if (date) {
      for (iFormat = 0; iFormat < format.length; iFormat++) {
        if (literal) {
          if (format.charAt(iFormat) === '\'' && !lookAhead('\'')) {
            literal = false;
          } else {
            output += format.charAt(iFormat);
          }
        } else {
          switch (format.charAt(iFormat)) {
            case 'd':
              output += formatNumber('d', date.getDate(), 2);
              break;
            case 'D':
              output += formatName('D', date.getDay(), this.locale.dayNamesShort, this.locale.dayNames);
              break;
            case 'o':
              output += formatNumber('o',
                Math.round(
                  (
                    new Date(date.getFullYear(), date.getMonth(),
                    date.getDate()).getTime() - new Date(date.getFullYear(), 0, 0).getTime()
                  ) / 86400000), 3
              );
              break;
            case 'm':
              output += formatNumber('m', date.getMonth() + 1, 2);
              break;
            case 'M':
              output += formatName('M', date.getMonth(), this.locale.monthNamesShort, this.locale.monthNames);
              break;
            case 'y':
              output += (lookAhead('y') ? date.getFullYear() :
                (date.getFullYear() % 100 < 10 ? '0' : '') + date.getFullYear() % 100);
              break;
            case '@':
              output += date.getTime();
              break;
            case '!':
              output += date.getTime() * 10000 + this.ticksTo1970;
              break;
            case '\'':
              if (lookAhead('\'')) {
                output += '\'';
              } else {
                literal = true;
              }

              break;
            default:
              output += format.charAt(iFormat);
          }
        }
      }
    }
    return output;
  }

  formatTime(date) {
    if (!date) {
      return '';
    }

    let output = '';
    let hours = date.getHours();
    const minutes = date.getMinutes();
    const seconds = date.getSeconds();

    if (this.hourFormat === '12' && this.pm && hours !== 12) {
      hours -= 12;
    }

    output += (hours < 10) ? '0' + hours : hours;
    output += ':';
    output += (minutes < 10) ? '0' + minutes : minutes;

    if (this.showSeconds) {
      output += ':';
      output += (seconds < 10) ? '0' + seconds : seconds;
    }

    if (this.hourFormat === '12') {
      output += this.pm ? ' PM' : ' AM';
    }

    return output;
  }

  parseTime(value) {
    const tokens: string[] = value.split(':');
    const validTokenLength = this.showSeconds ? 3 : 2;

    if (tokens.length !== validTokenLength) {
      throw new Error('Invalid time');
    }

    let h = parseInt(tokens[0], 10);
    const m = parseInt(tokens[1], 10);
    const s = this.showSeconds ? parseInt(tokens[2], 10) : null;

    if (isNaN(h) || isNaN(m) || h > 23 || m > 59 || (this.hourFormat === '12' && h > 12) || (this.showSeconds && (isNaN(s) || s > 59))) {
      throw new Error('Invalid time');
    } else {
      if (this.hourFormat === '12' && h !== 12 && this.pm) {
        h += 12;
      }

      return {hour: h, minute: m, second: s};
    }
  }

  // Ported from jquery-ui datepicker parseDate
  parseDate(value, format) {
    if (format == null || value == null) {
      throw new Error('Invalid arguments');
    }

    value = typeof value === 'object' ? value.toString() : value + '';
    if (value === '') {
      return null;
    }

    let iFormat, dim, extra,
      iValue = 0,
      year = -1,
      month = -1,
      day = -1,
      doy = -1,
      literal = false,
      date;
    const shortYearCutoff = (typeof this.shortYearCutoff !== 'string'
        ? this.shortYearCutoff
        : new Date().getFullYear() % 100 + parseInt(this.shortYearCutoff, 10)),
      lookAhead = (match) => {
        const matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) === match);
        if (matches) {
          iFormat++;
        }
        return matches;
      },
      getNumber = (match) => {
        const isDoubled = lookAhead(match),
          size = (match === '@' ? 14 : (match === '!' ? 20 :
            (match === 'y' && isDoubled ? 4 : (match === 'o' ? 3 : 2)))),
          minSize = (match === 'y' ? size : 1),
          digits = new RegExp('^\\d{' + minSize + ',' + size + '}'),
          num = value.substring(iValue).match(digits);
        if (!num) {
          throw new Error('Missing number at position ' + iValue);
        }
        iValue += num[0].length;
        return parseInt(num[0], 10);
      },
      getName = (match, shortNames, longNames) => {
        let index = -1;
        const arr = lookAhead(match) ? longNames : shortNames;
        const names = [];

        for (let i = 0; i < arr.length; i++) {
          names.push([i, arr[i]]);
        }
        names.sort((a, b) => {
          return -(a[1].length - b[1].length);
        });

        for (let i = 0; i < names.length; i++) {
          const name = names[i][1];
          if (value.substr(iValue, name.length).toLowerCase() === name.toLowerCase()) {
            index = names[i][0];
            iValue += name.length;
            break;
          }
        }

        if (index !== -1) {
          return index + 1;
        } else {
          throw new Error('Unknown name at position ' + iValue);
        }
      },
      checkLiteral = () => {
        if (value.charAt(iValue) !== format.charAt(iFormat)) {
          throw new Error('Unexpected literal at position ' + iValue);
        }
        iValue++;
      };

    for (iFormat = 0; iFormat < format.length; iFormat++) {
      if (literal) {
        if (format.charAt(iFormat) === '\'' && !lookAhead('\'')) {
          literal = false;
        } else {
          checkLiteral();
        }
      } else {
        switch (format.charAt(iFormat)) {
          case 'd':
            day = getNumber('d');
            break;
          case 'D':
            getName('D', this.locale.dayNamesShort, this.locale.dayNames);
            break;
          case 'o':
            doy = getNumber('o');
            break;
          case 'm':
            month = getNumber('m');
            break;
          case 'M':
            month = getName('M', this.locale.monthNamesShort, this.locale.monthNames);
            break;
          case 'y':
            year = getNumber('y');
            break;
          case '@':
            date = new Date(getNumber('@'));
            year = date.getFullYear();
            month = date.getMonth() + 1;
            day = date.getDate();
            break;
          case '!':
            date = new Date((getNumber('!') - this.ticksTo1970) / 10000);
            year = date.getFullYear();
            month = date.getMonth() + 1;
            day = date.getDate();
            break;
          case '\'':
            if (lookAhead('\'')) {
              checkLiteral();
            } else {
              literal = true;
            }
            break;
          default:
            checkLiteral();
        }
      }
    }

    if (iValue < value.length) {
      extra = value.substr(iValue);
      if (!/^\s+/.test(extra)) {
        throw new Error('Extra/unparsed characters found in date: ' + extra);
      }
    }

    if (year === -1) {
      year = new Date().getFullYear();
    } else if (year < 100) {
      year += new Date().getFullYear() - new Date().getFullYear() % 100 +
        (year <= shortYearCutoff ? 0 : -100);
    }

    if (doy > -1) {
      month = 1;
      day = doy;
      do {
        dim = this.getDaysCountInMonth(year, month - 1);
        if (day <= dim) {
          break;
        }
        month++;
        day -= dim;
      } while (true);
    }

    date = this.daylightSavingAdjust(new Date(year, month - 1, day));
    if (date.getFullYear() !== year || date.getMonth() + 1 !== month || date.getDate() !== day) {
      throw new Error('Invalid date'); // E.g. 31/02/00
    }
    return date;
  }

  daylightSavingAdjust(date) {
    if (!date) {
      return null;
    }
    date.setHours(date.getHours() > 12 ? date.getHours() + 2 : 0);
    return date;
  }

  updateFilledState() {
    this.filled = this.inputFieldValue && this.inputFieldValue !== '';
  }

  bindDocumentClickListener() {
    if (!this.documentClickListener) {
      this.documentClickListener = this.renderer.listenGlobal('body', 'click', () => {
        if (this.closeOverlay) {
          this.overlayVisible = false;
        }

        this.closeOverlay = true;
        this.dateClick = false;
      });
    }
  }

  unbindDocumentClickListener() {
    if (this.documentClickListener) {
      this.documentClickListener();
    }
  }

  ngOnDestroy() {
    this.unbindDocumentClickListener();

    if (!this.inline && this.appendTo) {
      this.el.nativeElement.appendChild(this.overlay);
    }
  }

  validate(c: AbstractControl) {
    if (!this._isValid) {
      return {invalidDate: true};
    }

    return null;
  }
}
