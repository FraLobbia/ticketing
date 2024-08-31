export interface RegisterModel {
  name: string;
  surname: string;
  email: string;
  password: string;
}

export interface LoginModel {
  email: string;
  password: string;
}

export interface LoginResponseModel {
  accessToken: string;
}

export interface JwtPayload {
  sub: string; // Email dell'utente (o identificatore principale)
  iat: number; // Issued At: Timestamp di quando il token è stato emesso
  idAccount: number; // Identificatore dell'account dell'utente
  roles: string; // Ruolo o ruoli dell'utente (potrebbe essere una stringa o un array di stringhe)
  exp: number; // Expiration: Timestamp di quando il token scade
}
