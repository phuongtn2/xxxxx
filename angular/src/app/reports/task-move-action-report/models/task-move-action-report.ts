import { TaskMoveActionRow } from './task-move-action-row';

export class TaskMoveActionReport {
  licensePlate = '';
  zoneName = '';
  rows: any[] = [];
  totalSpeedometer = 0;

  parse(data) {
    if (Array.isArray(data)) {
      data.forEach(
        value => {
          if (!this.licensePlate) {
            this.licensePlate = value['licensePlate'];
            this.zoneName = value['zoneName'];
          } else if (this.licensePlate !== value['licensePlate'] || this.zoneName !== value['zoneName']) {
            throw new Error('Invalid data.');
          }

          const newRow = new TaskMoveActionRow();
          newRow.startSpeedometer = value['startSpeedometer'];
          newRow.endSpeedometer = value['endSpeedometer'];
          newRow.actualSpeedometer = value['actualSpeedometer'];
          newRow.explanation = value['explanation'];
          newRow.moveStartDateString = value['moveStartDateString'];
          newRow.hourStart = value['hourStart'];
          newRow.hourEnd = value['hourEnd'];

          this.totalSpeedometer += newRow.actualSpeedometer;
          this.rows.push(newRow);
        }
      );
    }
  }

  reset() {
    this.licensePlate = '';
    this.zoneName = '';
    this.rows = [];
    this.totalSpeedometer = 0;
  }
}
