import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ticket } from '../../../shared/models/ticket.model';
// Supponendo che tu abbia un modello per il Ticket

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private baseUrl = 'http://localhost:8080/api/tickets'; // URL di base del tuo backend

  constructor(private http: HttpClient) {}

  // Metodo per creare un nuovo ticket
  createTicket(ticket: Ticket): Observable<Ticket> {
    return this.http.post<Ticket>(`${this.baseUrl}`, ticket);
  }

  // Metodo per ottenere un ticket per ID
  getTicketById(id: number): Observable<Ticket> {
    return this.http.get<Ticket>(`${this.baseUrl}/${id}`);
  }

  // Metodo per ottenere tutti i ticket
  getAllTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.baseUrl);
  }

  // Metodo per aggiornare un ticket
  updateTicket(id: number, ticket: Ticket): Observable<Ticket> {
    return this.http.put<Ticket>(`${this.baseUrl}/${id}`, ticket);
  }

  // Metodo per cancellare un ticket
  deleteTicket(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
