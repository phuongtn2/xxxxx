import { Farmer } from './farmer';
export class Technician {
  id: number;
  name: string;
  farmers: Farmer[] = [];
}
