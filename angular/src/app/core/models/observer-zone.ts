import { Company } from './company';
import { Zone } from './zone';

export class ObserverZone {
  id: number;
  employeeId: number;
  firstName: string;
  lastName: string;
  fullName: string;
  phone: string;
  companyId: number;
  cultivationZoneId: number;
  observerZoneId: number;
  zone: Zone;
  company: Company;
}
