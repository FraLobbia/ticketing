export interface Account {
  id: number;
  name?: string;
  surname?: string;
  email?: string;
  roles?: RoleEnum[];
  profilePictureUrl?: string;
}

export enum RoleEnum {
  ROLE_USER = 'ROLE_USER',
  ROLE_ADMIN = 'ROLE_ADMIN',
}
