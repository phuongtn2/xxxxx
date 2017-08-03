import { MoveAction } from '../../core/models/move-action';
import { TaskReport } from '../../core/models/task-report';

export class ScheduleUpdate {
  clientPrimaryKey: number;
  id: number;
  registrationId: number;
  nurseryId: number;
  cultivationId: number;
  title: string;
  description: string;
  startDate: number;
  dueDate: number;
  morningTask: string;
  afternoonTask: string;
  workStartDate: number;
  workDueDate: number;
  provinceId: number;
  districtId: number;
  wardId: number;
  status: number;
  taskReports: TaskReport[];
  moveActions: MoveAction[];
}
