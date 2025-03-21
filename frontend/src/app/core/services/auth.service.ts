import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import {
  IJwtPayload,
  ILogin,
  IRegister,
} from '../../shared/interfaces/auth.interface';
import { Router } from '@angular/router';
import { ILoginResponse } from '../../shared/interfaces/auth.interface';
import { jwtDecode } from 'jwt-decode';
import { Account } from '../../shared/models/account.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private url = environment.API_URL;
  private tokenKey = environment.SECRET_KEY;

  isAdmin = false;

  constructor(private http: HttpClient, private router: Router) { }

  /**
   * Effettua il login dell'utente.
   *  @param email - Email utente
   *  @param password - Password
   * @returns Observable contenente la risposta del server
   */
  login({ email, password }: ILogin): Observable<ILoginResponse> {
    const url = `${this.url}/auth/login`;
    const body = { email, password };
    return this.http.post<ILoginResponse>(url, body).pipe(
      tap((response) => this.setToken(response.accessToken)), // Salva il token
      catchError(this.handleError<ILoginResponse>('login'))
    );
  }

  /**
   * Registra un nuovo utente.
   *  @param name - Nome utente
   *  @param surname - Cognome utente
   *  @param email - Email utente
   *  @param password - Password
   * @returns Observable contenente la risposta del server
   */
  register({
    name,
    surname,
    email,
    password,
  }: IRegister): Observable<Account> {
    const url = `${this.url}/auth/register`;
    const body = { name, surname, email, password };
    return this.http
      .post<Account>(url, body)
      .pipe(catchError(this.handleError<Account>('register')));
  }

  /**
   * Controlla se l'utente è autenticato.
   * @returns true se l'utente è autenticato, altrimenti false
   */ isAuthenticated(): boolean {
    const token = this.getToken();
    // Puoi aggiungere ulteriori controlli, ad esempio, verificare la scadenza del token
    return !!token;
  }

  /**
   * Effettua il logout dell'utente.
   */ logout(): void {
    this.removeToken();
    this.router.navigate(['/auth/login']);
  }

  /**
   * Salva il token di autenticazione.
   * @param token - Token da salvare
   */ private setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  /**
   * Recupera il token di autenticazione.
   * @returns Il token salvato, o null se non è presente
   */ getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  /**
   * Rimuove il token di autenticazione.
   */ private removeToken(): void {
    localStorage.removeItem(this.tokenKey);
  }

  /**
   * Recupera l'ID utente dal token di autenticazione.
   *  @param token - Token di autenticazione
   * @returns ID utente, o null se non è possibile decodificare il token
   */ getUserIdFromToken(): number | null {
    try {
      const token = this.getToken();
      if (!token) return null;
      const decodedToken: IJwtPayload = jwtDecode(token);
      console.log('Decoded token:', decodedToken);
      return decodedToken && decodedToken.idAccount
        ? decodedToken.idAccount
        : null;
    } catch (error) {
      console.error('Error decoding token', error);
      return null;
    }
  }

  /**
   * Gestione degli errori HTTP.
   *  @param operation - Nome dell'operazione che ha causato l'errore
   *  @param result - Valore opzionale da restituire come risultato dell'osservabile
   * @returns Funzione che restituisce un osservabile
   */ private handleError<T>(operation = 'operation', result?: T) {
    return (error: Error): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
