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
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit, OnDestroy {
  /**
   * Property
   */
  tickets: Ticket[] = [];
  availableStatus = Object.values(TicketStatusEnum);
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

  applyStatusFilter(event: Event): void {
    const target = event.target as HTMLSelectElement;
    if (target) {
      const filterValue = target.value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
    }
  }

  /**
   * Metodo per aggiungere un ticket alla sidebar
   */
  addTicketToSidebar(ticket: Ticket): void {
    this.ticketService.addViewingTicket(ticket.id!);
  }

  /**
   * Metodo per filtrare i ticket nella tabella material
   */
  applyFilter(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target) {
      const filterValue = target.value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
    }
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
