import { File } from './file';

export class Media {
  clientPrimaryKey: number;
  id: number;
  phase: string;
  actionId: number;
  fileId: number;
  delFlag: number;
  file: File;
}
