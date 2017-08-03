import { User } from './user';
import { FarmerRelative } from './farmerRelative';

export class Farmer extends User {
  farmerCode: string;
  inCurrentCrop: boolean;
  farmerRelatives: FarmerRelative[];
}
