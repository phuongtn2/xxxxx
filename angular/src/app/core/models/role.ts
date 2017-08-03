import { RoleSlug } from '../enum/role-slug.enum';

export class Role {
  id: number;
  parentRoleId: number;
  parentRole: Role;
  roleName: string;
  slug: RoleSlug;
  scope: string;
}
