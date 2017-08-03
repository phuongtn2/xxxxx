import { Farmer } from '../../core/models/farmer';
import { Province } from '../../core/models/location/province';
import { District } from '../../core/models/location/district';
import { Ward } from '../../core/models/location/ward';
export class FarmerSche extends Farmer {
  province: Province;
  district: District;
  ward: Ward;
}
