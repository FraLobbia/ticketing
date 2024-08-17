export interface Account {
  id: number;
  name: string;
  surname: string;
  email: string;
  roles: RoleEnum[];
  profilePictureUrl?: string; // Opzionale, se decidi di includere l'URL dell'immagine del profilo
}

export enum RoleEnum {
  ROLE_USER = 'ROLE_USER',
  ROLE_ADMIN = 'ROLE_ADMIN',
}
