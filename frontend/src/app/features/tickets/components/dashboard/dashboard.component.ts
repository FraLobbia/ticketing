import {
  Component,
  HostListener,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { Ticket } from '../../../../shared/models/ticket.model';
import { TicketService } from '../../services/ticket.service';
import { TicketStatusEnum } from '../../../../shared/models/ticket.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { getTicketStatusEnumValue } from '../../../../shared/utility/string-editor.utility';
import { MatSelectChange } from '@angular/material/select';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit, OnDestroy {
  /**
   * Property
   */
  textFilter: string = '';
  statusFilter: string = '';
  tickets: Ticket[] = [];
  availableStatus = Object.keys(TicketStatusEnum);
  displayedColumns: string[] = [
    'id',
    'title',
    'status',
    'createdBy',
    'createdAt',
  ];
  getTicketStatusEnumValue = getTicketStatusEnumValue;
  dataSource: MatTableDataSource<Ticket> = new MatTableDataSource();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  /**
   * costruttore
   */
  constructor(private ticketService: TicketService) {}

  /**
   * Inizializza il componente
   */
  ngOnInit(): void {
    this.loadTickets();
  }

  /**
   * Carica i ticket dal servizio
   */
  private loadTickets(): void {
    this.ticketService
      .getAllTickets()
      .pipe(takeUntil(this.destroy$))
      .subscribe((tickets: Ticket[]) => {
        this.tickets = tickets;
        this.dataSource = new MatTableDataSource(tickets);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      });
  }

  /**
   * Evento di resize della finestra
   */
  @HostListener('window:resize', ['$event'])
  onResize(event: Event): void {
    this.updateDisplayedColumns(); // Aggiorna le colonne al ridimensionamento della finestra
  }

  /**
   * Aggiorna le colonne visualizzate in base alla larghezza della finestra
   */
  updateDisplayedColumns(): void {
    if (window.innerWidth < 900) {
      this.displayedColumns = ['title', 'status'];
    } else {
      this.displayedColumns = ['title', 'status', 'createdBy', 'createdAt'];
    }
  }

  /**
   * Metodo per aggiungere un ticket alla sidebar
   */
  addTicketToSidebar(ticket: Ticket): void {
    this.ticketService.addViewingTicket(ticket.id!);
  }

  // Variabili per i filtri
  selectedStatus: string[] = []; // Stati selezionati

  /**
   * Seleziona tutti gli stati e aggiorna la UI.
   * @param select MatSelect
   */
  selectAll(select: any): void {
    this.selectedStatus = [];
    select.close(); // Chiude il menu a tendina
    this.applyFilter('', this.selectedStatus); // Applica il filtro
  }

  /**
   * Gestisce la selezione manuale delle opzioni.
   * @param event Evento di cambiamento
   */
  onStatusChange(event: any): void {
    this.selectedStatus = event.value;
    this.applyFilter('', this.selectedStatus);
  }

  /**
   * Applica un filtro (testuale o stato) alla tabella.
   * @param text Testo da filtrare
   * @param status Stato da filtrare
   */
  applyFilter(text: string, status: string[] | string): void {
    // Gestione del filtro testuale
    const textFilter = text ? text.trim().toLowerCase() : '';

    // Gestione del filtro per stato
    const statusFilter =
      Array.isArray(status) && status.includes('all')
        ? [] // Nessun filtro sugli stati (Tutti)
        : Array.isArray(status)
        ? status.map((s) => s.toLowerCase())
        : [status.toLowerCase()];

    // Debug dei filtri
    console.log('*** DEBUG FILTERS ***', { textFilter, statusFilter });

    // Configura il filterPredicate
    this.dataSource.filterPredicate = (data: Ticket, filter: string) => {
      const filters = JSON.parse(filter);
      const matchesText = data.title.toLowerCase().includes(filters.text);
      const matchesStatus =
        filters.status.length === 0 || // Se nessuno stato è selezionato, mostra tutti
        filters.status.includes(data.status.toLowerCase());

      return matchesText && matchesStatus;
    };

    // Aggiorna il filtro della tabella
    this.dataSource.filter = JSON.stringify({
      text: textFilter,
      status: statusFilter,
    });
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
