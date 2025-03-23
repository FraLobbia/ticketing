export interface IRegister {
  name: string;
  surname: string;
  email: string;
  password: string;
}

export interface ILogin {
  email: string;
  password: string;
}
/**
 * Contiene il token di accesso restituito dal server dopo il login
 */
export interface ILoginResponse {
  accessToken: string;
  exceptionCode?: string;
  message?: string;
}

/**
 * Contiene i dati dell'utente autenticato
 */
export interface IJwtPayload {
  sub: string;      // Email dell'utente (o identificatore principale)
  iat: number;      // Issued At: Timestamp di quando il token è stato emesso
  idAccount: number;// Identificatore dell'account dell'utente
  name: string;     // Nome dell'utente
  surname: string;  // Cognome dell'utente
  roles: string[];  // Ruolo o ruoli dell'utente (array di stringhe)
  exp: number;      // Expiration: Timestamp di quando il token scade
}
