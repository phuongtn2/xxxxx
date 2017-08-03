import { TaskPlan } from './task-plan';

export class TaskPlanReport {
  beginThisWeek: Date;
  endThisWeek: Date;
  beginNextWeek: Date;
  endNextWeek: Date;
  thisWeek: TaskPlan[] = [];
  nextWeek: TaskPlan[] = [];
  currentSpeedometer: number;
  dateReport: Date;

  constructor() {
    this.beginThisWeek = new Date();
    this.beginThisWeek.setDate(this.beginThisWeek.getDate() - this.getDay(this.beginThisWeek));
    this.endThisWeek = new Date(this.beginThisWeek);
    this.endThisWeek.setDate(this.endThisWeek.getDate() + 6);

    this.beginNextWeek = new Date(this.endThisWeek);
    this.beginNextWeek.setDate(this.beginNextWeek.getDate() + 1);
    this.endNextWeek = new Date(this.beginNextWeek);
    this.endNextWeek.setDate(this.endNextWeek.getDate() + 6);
  }

  parse(data) {
    if (Array.isArray(data)) {
      data.forEach(
        value => {
          const plan = new TaskPlan();
          plan.description = value['description'];
          plan.startDate = value['startDate'];
          plan.morningTask = value['morningTask'];
          plan.afternoonTask = value['afternoonTask'];
          plan.provinceName = value['provinceName'];
          plan.districtName = value['districtName'];

          if (plan.startDate < this.beginNextWeek.getTime()) {
            this.thisWeek.push(plan);
          } else {
            this.nextWeek.push(plan);
          }

          const dateReport = new Date(value['dateReport']);
          dateReport.setHours(0, 0, 0, 0);
          if (!this.currentSpeedometer) {
            this.currentSpeedometer = value['currentSpeedometer'];
            this.dateReport = dateReport;
          } else if (
            this.currentSpeedometer !== value['currentSpeedometer'] ||
            this.dateReport.getTime() !== dateReport.getTime()
          ) {
            throw new Error('Invalid data.');
          }
        }
      );
    }
  }

  reset() {
    this.thisWeek = [];
    this.nextWeek = [];
    this.currentSpeedometer = null;
    this.dateReport = null;
  }

  private getDay(date: Date) {
    return (date.getDay() + 6) % 7;
  }
}
