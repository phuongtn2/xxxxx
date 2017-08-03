import { BaseModel } from './base-model';
import { RoleSlug } from '../enum/role-slug.enum';
import { Role } from './role';
import { Operation } from './operation';

export class User extends BaseModel {
  id?: number;
  uId?: number;
  userId?: string;
  username?: string;
  fullName: string;
  firstName: string;
  lastName: string;
  roles: Role[];
  lastLoginDate: number;
  operations: Operation[];
  departmentId: number;
  divisionId: number;
  companyId: number;
  identityCard: string;
  email: string;
  phone: string;
  address: string;
  townId: number;
  districtId: number;
  provinceId: number;
  timeZoneId: string;
  languageId: string;
  currentPassword: string;
  loginIP: string;
  lastLoginIP: string;
  loginDate: number;
  deviceType: string;
  errors: any;
  status: number;
  wardId: number;
  password: string;

  get roleSlug(): RoleSlug {
    const firstRole = this.roles && this.roles.find(value => true);
    return firstRole && firstRole.slug;
  }

  set roleSlug(value) {
    if (!this.roles) { // TODO(dtrthi): review this
      this.roles = [];
    }
    this.roles.length = 0;
    this.roles.push(<Role>{slug: new RoleSlug(value.toString())});
  }

  employeeCode: string;
  position: string;

  get isSuperAdministrator() {
    return !!(this.roles && this.roles.find(role => role.slug.toString() === RoleSlug.SuperAdministrator.value));
  }

  get isTechnician() {
    return !!(this.roles && this.roles.find(role => role.slug.toString() === RoleSlug.Technician.value));
  }

  get isTechnicianLeader() {
    return !!(this.roles && this.roles.find(role => role.slug.toString() === RoleSlug.TechnicianLeader.value));
  }

  get isStorekeeper() {
    return !!(this.roles && this.roles.find(role => role.slug.toString() === RoleSlug.Storekeeper.value));
  }

  get isAccountant() {
    return !!(this.roles && this.roles.find(role => role.slug.toString() === RoleSlug.Accountant.value));
  }

  get isSupervisor() {
    return !!(this.roles && this.roles.find(role => role.slug.toString() === RoleSlug.Supervisor.value));
  }

  get isSubDirector() {
    return !!(this.roles && this.roles.find(role => role.slug.toString() === RoleSlug.SubDirector.value));
  }

  get isDirector() {
    return !!(this.roles && this.roles.find(role => role.slug.toString() === RoleSlug.Director.value));
  }

  toString() {
    return JSON.stringify(this);
  }
}
