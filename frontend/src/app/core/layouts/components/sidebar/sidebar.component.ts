import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Ticket } from '../../../../shared/models/ticket.model';
import { TicketService } from '../../../../features/tickets/services/ticket.service';
import { Subject, takeUntil } from 'rxjs';
import { MENU_ITEMS } from '../../../../shared/constants/menu.constants';
import { IMenuItem } from '../../../../shared/interfaces/menu-item.interface';
import { getTicketStatusEnumValue } from '../../../../shared/utility/string-editor.utility';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent implements OnInit, OnDestroy {
  /**
   * Campi
   */
  viewingTickets: Ticket[] = [];
  menuItems: IMenuItem[] = MENU_ITEMS;
  getTicketStatusEnumValue = getTicketStatusEnumValue;

  /**
   * costruttore
   */
  constructor(private router: Router, private ticketService: TicketService) {}
  /**
   * Inizializza il componente
   * 1. Chiama il metodo per aggiornare i ticket visualizzati 'updateViewingTicketsSubject'
   * 2. Sottoscrizione al subject per ricevere i ticket visualizzati ogni volta che cambiano
   */
  ngOnInit(): void {
    this.ticketService['updateViewingTicketsSubject']();
    this.ticketService.viewingTickets$
      .pipe(takeUntil(this.destroy$))
      .subscribe((tickets: Ticket[]) => {
        this.viewingTickets = tickets;
      });
  }

  /**
   * Gestisce la navigazione tra le voci del menu e rende attiva solo la voce cliccata
   * @param clickedItem - La voce del menu cliccata e verso la quale si vuole navigare
   */
  handleMenuNavigation(clickedItem: IMenuItem): void {
    this.menuItems = this.menuItems.map((item) => ({
      ...item,
      isActive: item.path === clickedItem.path,
    }));
    this.router.navigate([clickedItem.path]);
  }

  /**
   * Restituisce true se il ticket è selezionato, altrimenti false
   * Lo confronta con l'url attuale per determinare se il ticket è quello visualizzato al momento nella pagina
   * @param id - id del ticket da confrontare con l'url attuale
   * @returns boolean
   */
  isSelectedTicket(id: number): boolean {
    return this.router.url === '/tickets/' + id;
  }

  /**
   * Gestisce la rimozione di un ticket dai ticket visualizzati nella sidebar
   *
   * @param id - id del ticket da rimuovere
   * @returns void
   */
  removeViewingTicket(id: number): void {
    this.ticketService.removeViewingTicket(id);
  }

  /**
   * Destroy del componente e delle sottoscrizioni
   */
  destroy$ = new Subject<void>();
  ngOnDestroy(): void {
    this.destroy$.next(); // Emissione del segnale per interrompere le sottoscrizioni
    this.destroy$.complete();
  }
}
