import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Ticket } from '../../../../shared/models/ticket.model';
import { TicketService } from '../../../../features/tickets/services/ticket.service';

interface MenuItem {
  path: string;
  title: string;
  icon: string;
  isActive: boolean;
}

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent implements OnInit {
  viewingTickets: Ticket[] = [];
  menuItems = [
    {
      title: 'Dashboard',
      icon: 'dashboard',
      path: '/tickets',
      isActive: false,
    },
    // {
    //   title: 'Apri Nuovo Ticket',
    //   icon: 'add_circle',
    //   path: '/tickets/create',
    //   isActive: false,
    // },
    // Aggiungi qui altre voci di menu
  ];

  // costruttore
  constructor(private router: Router, private ticketService: TicketService) {}
  ngOnInit(): void {
    // Inizializza i viewingTickets della sidebar con i ticket già presenti nel local storage
    this.ticketService.getViewingTicketsFromDb().subscribe((tickets) => {
      this.viewingTickets = tickets;
    });
    /**
     * Sottoscrizione all'observable viewingTickets$ del servizio TicketService per riflettere in tempo reale i cambiamenti nei viewingTickets. Essenziale non fermarlo con take(1) altrimenti non riceveremo più aggiornamenti.
     */
    this.ticketService.viewingTickets$.subscribe((tickets) => {
      this.viewingTickets = tickets;
    });
  }

  /**
   * Permette di verificare se un menu item è selezionato e quindi visualizzato.
   *
   * @param selectedItem - Il menu item selezionato
   * @returns void
   */
  isMenuItemSelected(selectedItem: MenuItem): void {
    this.menuItems.forEach((item) => (item.isActive = false));
    selectedItem.isActive = true;
    this.router.navigate([selectedItem.path]);
  }

  /**
   * Permette di verificare se un ticket è selezionato e quindi visualizzato controllando l'url corrente.
   * @param id
   * @returns true se il ticket è selezionato, altrimenti false
   */
  isSelectedTicket(id: number): boolean {
    const currentPath = this.router.url;
    if (currentPath === '/tickets/' + id) {
      return true;
    }
    return false;
  }

  /**
   * Rimuove un ticket dalla sidebar
   *
   * @param ticketId - id del ticket da rimuovere
   * @returns void
   */
  removeViewingTicket(ticketId: number): void {
    this.ticketService.removeViewingTicket(ticketId);
  }
}