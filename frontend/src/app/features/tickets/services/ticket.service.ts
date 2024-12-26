import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap } from 'rxjs';
import { Ticket } from '../../../shared/models/ticket.model';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  // costruttore
  constructor(private http: HttpClient) {}

  private baseUrl = 'http://localhost:8080/api/tickets';

  /**
   * BehaviorSubject che emette i ticket visualizzati nella sidebar.
   */
  private viewingTicketsSubject = new BehaviorSubject<Ticket[]>([]);
  /**
   * Observable (associato al BehaviorSubject viewingTicketsSubject) a cui iscriversi per ricevere i ticket visualizzati nella sidebar.
   */
  viewingTickets$ = this.viewingTicketsSubject.asObservable();

  /**
   * Aggiorna i ticket visualizzati nel localStorage e nel BehaviorSubject.
   * Metodo chiamato ogni volta che viene aggiunto o rimosso un ticket dalla sidebar.
   * Necessario per mantenere aggiornati i ticket visualizzati nella sidebar in tempo reale.
   * @returns void
   */
  public updateViewingTicketsSubject(): void {
    // Cambiato da private a public
    this.getViewingTicketsFromDB().subscribe((updatedTickets) => {
      this.viewingTicketsSubject.next(updatedTickets);
    });
  }

  createTicket(ticket: Ticket): Observable<Ticket> {
    return this.http.post<Ticket>(`${this.baseUrl}`, ticket);
  }

  getTicketById(id: number): Observable<Ticket> {
    return this.http.get<Ticket>(`${this.baseUrl}/${id}`);
  }

  getAllTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.baseUrl);
  }

  updateTicket(id: number, ticket: Ticket): Observable<Ticket> {
    return this.http.put<Ticket>(`${this.baseUrl}/${id}`, ticket);
  }

  deleteTicket(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  /**
   * Aggiunge l'id di un ticket al localStorage
   *
   * @param id - id del ticket da aggiungere
   * @returns void
   */
  addViewingTicket(id: number): void {
    const tickets: number[] = this.getViewingTicketsIdFromLocalStorage();
    if (!tickets.includes(id)) {
      tickets.push(id);
    }
    localStorage.setItem('viewingTickets', JSON.stringify(tickets));
    this.updateViewingTicketsSubject();
  }

  /**
   * Rimuove l'id di un ticket dal localStorage
   *
   * @param ticketId - id del ticket da rimuovere
   * @returns void
   */
  removeViewingTicket(ticketId: number): void {
    const tickets: number[] = this.getViewingTicketsIdFromLocalStorage();
    const updatedTickets = tickets.filter((id) => id !== ticketId);
    localStorage.setItem('viewingTickets', JSON.stringify(updatedTickets));
    const updatedViewingTickets = this.viewingTicketsSubject.value.filter(
      (ticket) => ticket.id !== ticketId
    );
    this.viewingTicketsSubject.next(updatedViewingTickets);
    this.updateViewingTicketsSubject();
  }

  /**
   * Restituisce un array di ticket ID se trovati nel localStorage, altrimenti un array vuoto.
   *
   * @returns number[] | []
   */
  private getViewingTicketsIdFromLocalStorage(): number[] | [] {
    const ticketIds = localStorage.getItem('viewingTickets');
    return ticketIds ? JSON.parse(ticketIds) : [];
  }

  /**
   * Prima ottiene gli ID dei ticket visualizzati dal localStorage, quindi fa una richiesta GET per ottenere i ticket visualizzati.
   *
   * @returns Ticket[]
   */
  getViewingTicketsFromDB(): Observable<Ticket[]> {
    const ticketIds: number[] = this.getViewingTicketsIdFromLocalStorage();
    console.group('Ottengo i viewing ticket dal DB');
    console.table(ticketIds);
    console.groupEnd();
    return this.http.get<Ticket[]>(`${this.baseUrl}/viewing-tickets`, {
      params: { ids: ticketIds.join(',') },
    });
  }

  updateTicketStatus(id: number, status: string): Observable<Ticket> {
    return this.http
      .put<Ticket>(`${this.baseUrl}/status/${id}`, { status })
      .pipe(
        tap((updatedTicket: Ticket) => {
          // Aggiorna i viewingTickets
          this.updateViewingTicketsSubject();
        })
      );
  }
}
