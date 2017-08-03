export class RoleSlug {
  static SuperAdministrator = new RoleSlug('super_aministrator');

  static User = new RoleSlug('user');

  static Technician = new RoleSlug('technician');

  static TechnicianLeader = new RoleSlug('technician_leader');

  static Storekeeper = new RoleSlug('storekeeper');

  static Accountant = new RoleSlug('accountant');

  static Supervisor = new RoleSlug('supervisor');

  static SubDirector = new RoleSlug('sub_director');

  static Director = new RoleSlug('director');

  constructor(public value: string) { }

  toString() { return this.value; }
}

export const RoleSlugs = (roleSlug) => {
  if (roleSlug.toString() === RoleSlug.Accountant.value) {
    return [
      {key: RoleSlug.Technician, value: 'Kỹ Thuật Viên'},
      {key: RoleSlug.TechnicianLeader, value: 'Tổ Trưởng'},
      {key: RoleSlug.Storekeeper, value: 'Thủ Kho'}
    ];
  }

  if (roleSlug.toString() === RoleSlug.TechnicianLeader.value) {
    return [
      {key: RoleSlug.Technician, value: 'Kỹ Thuật Viên'}
    ];
  }

  if (roleSlug.toString() === RoleSlug.SubDirector.value || roleSlug.toString() === RoleSlug.Director.value) {
    const items = [
      {key: RoleSlug.Technician, value: 'Kỹ Thuật Viên'},
      {key: RoleSlug.TechnicianLeader, value: 'Tổ Trưởng'},
      {key: RoleSlug.Storekeeper, value: 'Thủ Kho'},
      {key: RoleSlug.Accountant, value: 'Kế Toán'}
    ];
    if (roleSlug.toString() === RoleSlug.Director.value) {
      items.push({key: RoleSlug.SubDirector, value: 'Phó Giám Đốc'});
    }
    return items;
  }

  if (roleSlug.toString() === RoleSlug.SuperAdministrator.value) {
    return [
      {key: RoleSlug.Technician, value: 'Kỹ Thuật Viên'},
      {key: RoleSlug.TechnicianLeader, value: 'Tổ Trưởng'},
      {key: RoleSlug.Storekeeper, value: 'Thủ Kho'},
      {key: RoleSlug.Accountant, value: 'Kế Toán'},
      {key: RoleSlug.Supervisor, value: 'Giám Sát'},
      {key: RoleSlug.SubDirector, value: 'Phó Giám Đốc'},
      {key: RoleSlug.Director, value: 'Giám Đốc'}
    ];
  }

  return [];
};
