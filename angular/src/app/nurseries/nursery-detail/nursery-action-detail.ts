import { NurseryAction } from '../../core/enum/nursery-action.enum';
import { NurseryActionContent } from './nursery-action-content';

export class NurseryActionDetail {
  id: number;
  action: NurseryAction;
  actionUpdateDate: number;
  actionDate: number;
  actionName: string;
  contents: NurseryActionContent[];
  coordX: number;
  coordY: number;
  medias: any;
  target: string;
  targetResult?: string;
}
