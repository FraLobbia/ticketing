import {
  Component,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { Router } from '@angular/router';
import { Ticket } from '../../../../shared/models/ticket.model';
import { TicketService } from '../../../../features/tickets/services/ticket.service';
import { Subscription } from 'rxjs';

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
export class SidebarComponent implements OnInit, OnDestroy {
  /**
   * Variabili
   */
  private subscriptions: Subscription[] = [];
  viewingTickets: Ticket[] = [];

  /**
   * Menu di navigazione
   */
  menuItems = [
    {
      title: 'Dashboard',
      icon: 'dashboard',
      path: '/tickets',
      isActive: false,
    },
    {
      title: 'Ticket di oggi',
      icon: 'calendar_month',
      path: 'today',
      isActive: false,
    },
    {
      title: 'In pending',
      icon: 'pending_actions',
      path: 'pending',
      isActive: false,
    },
    {
      title: 'Impostazioni',
      icon: 'settings',
      path: 'settings',
      isActive: false,
    },
    // Aggiungi qui altre voci di menu
  ];

  /**
   * costruttore
   */
  constructor(private router: Router, private ticketService: TicketService) {}

  ngOnInit(): void {
    const ticketFromDbSubscription = this.ticketService
      .getViewingTicketsFromDb() // Inizializza i viewingTickets della sidebar con i ticket già presenti nel local storage
      .subscribe((tickets: Ticket[]) => {
        this.viewingTickets = tickets;
      });

    /**
     * Sottoscrizione all'observable viewingTickets$ del servizio TicketService per riflettere in tempo reale i cambiamenti nei viewingTickets. Essenziale non fermarlo con take(1) altrimenti non riceveremo più aggiornamenti.
     */
    const viewingTicketsSubscription =
      this.ticketService.viewingTickets$.subscribe((tickets: Ticket[]) => {
        this.viewingTickets = tickets;
      });

    // Aggiungi le sottoscrizioni all'array subscriptions per poterle eliminare in ngOnDestroy
    this.subscriptions.push(
      ticketFromDbSubscription,
      viewingTicketsSubscription
    );
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

  /**
   * Metodo che viene chiamato quando il componente viene distrutto. Serve per eliminare tutte le sottoscrizioni agli observable.
   */
  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
