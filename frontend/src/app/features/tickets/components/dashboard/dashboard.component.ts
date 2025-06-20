import {
  Component,
  HostListener,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { Ticket } from '../../../../shared/models/ticket.model';
import { TicketService } from '../../services/ticket.service';
import { TicketStatusEnum } from '../../../../shared/models/ticket.model';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { getTicketStatusEnumValue } from '../../../../shared/utility/string-editor.utility';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit, OnDestroy {
  // === Proprietà ===
  textFilter: string = '';
  selectedStatus: string[] = [];
  tickets: Ticket[] = [];
  paginatedTickets: Ticket[] = [];
  viewingTickets: Ticket[] = [];

  pageSize: number = 20;
  currentPage: number = 1;

  availableStatus: string[] = Object.keys(TicketStatusEnum);
  getTicketStatusEnumValue = getTicketStatusEnumValue;
  displayedColumns: string[] = ['id', 'title', 'status', 'author', 'createdAt'];

  sortColumn: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  private destroy$ = new Subject<void>();

  constructor(private ticketService: TicketService, private router: Router) { }

  // === Ciclo di vita ===
  ngOnInit(): void {
    this.loadTickets();
    this.ticketService.viewingTickets$.subscribe((tickets: Ticket[]) => {
      this.viewingTickets = tickets;
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  getColumnHeader(column: string): string {
    const headers: { [key: string]: string } = {
      id: 'ID',
      title: 'Title',
      status: 'Status',
      author: 'Account',
      createdAt: 'Created At',
    };
    return headers[column] || column;
  }

  columnAccessors: { [key: string]: (ticket: Ticket) => any } = {
    id: (t) => t.id,
    title: (t) => t.title,
    status: (t) => this.getTicketStatusEnumValue(t.status),
    author: (t) => `${t.author.name} ${t.author.surname}`,
    createdAt: (t) => `${new Date(t.createdAt).toLocaleDateString('it-IT', {
      weekday: 'long',
      day: '2-digit',
      month: 'long',
      year: 'numeric'
    })}`,
  };
  sortData(column: string): void {
    if (this.sortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }

    const direction = this.sortDirection === 'asc' ? 1 : -1;

    const getValue = (obj: any, path: string): any => {
      return path.split('.').reduce((acc, part) => acc && acc[part], obj);
    };

    this.tickets.sort((a, b) => {
      let valueA = getValue(a, column);
      let valueB = getValue(b, column);

      if (valueA == null) valueA = '';
      if (valueB == null) valueB = '';

      // Gestione stringhe/date/numeri
      if (typeof valueA === 'string') {
        valueA = valueA.toLowerCase();
        valueB = valueB.toLowerCase();
      } else if (valueA instanceof Date || column === 'createdAt') {
        valueA = new Date(valueA).getTime();
        valueB = new Date(valueB).getTime();
      }

      return valueA > valueB ? direction : valueA < valueB ? -direction : 0;
    });

    this.changePage(this.currentPage);
  }


  // === Caricamento dati ===
  private loadTickets(): void {
    this.ticketService
      .getAllTickets()
      .pipe(takeUntil(this.destroy$))
      .subscribe((tickets: Ticket[]) => {
        this.tickets = tickets;
        this.changePage(1); // inizializza paginazione
      });
  }

  // === Paginazione ===
  get totalPages(): number {
    return Math.ceil(this.filteredTickets().length / this.pageSize);
  }

  changePage(page: number): void {
    if (page < 1 || page > this.totalPages) return;
    this.currentPage = page;

    const allFiltered = this.filteredTickets();
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.paginatedTickets = allFiltered.slice(start, end);
  }

  // === Filtro ===
  applyFilter(text: string, status: string[] | string): void {
    this.textFilter = text;
    this.selectedStatus = Array.isArray(status) ? status : [status];
    this.changePage(1);
  }

  private filteredTickets(): Ticket[] {
    const text = this.textFilter.toLowerCase().trim();
    const status = this.selectedStatus.map(s => s.toLowerCase());

    return this.tickets.filter(ticket => {
      const matchesText = ticket.title.toLowerCase().includes(text);
      const matchesStatus =
        status.length === 0 || status.includes(ticket.status.toLowerCase());
      return matchesText && matchesStatus;
    });
  }

  // === Gestione selezione stato ===
  onCheckboxChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    const value = input.value;

    if (input.checked) {
      if (!this.selectedStatus.includes(value)) {
        this.selectedStatus.push(value);
      }
    } else {
      this.selectedStatus = this.selectedStatus.filter(s => s !== value);
    }

    this.applyFilter(this.textFilter, this.selectedStatus);
  }

  toggleSelectAll(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.checked) {
      this.selectedStatus = [...this.availableStatus];
    } else {
      this.selectedStatus = [];
    }
    this.applyFilter(this.textFilter, this.selectedStatus);
  }

  // === Click sulla riga della tabella ===
  onRowClick(event: MouseEvent, ticket: Ticket): void {
    this.ticketService.addViewingTicket(ticket.id!);

    if (event.shiftKey || event.altKey || event.ctrlKey) {
      event.preventDefault();
      return;
    }

    this.router.navigate(['/tickets', ticket.id]);
  }

  // === Responsive columns (non più necessaria con Bootstrap ma tenuta se utile) ===
  @HostListener('window:resize', ['$event'])
  onResize(): void {
    // Bootstrap è già responsive, ma manteniamo questo metodo se vuoi aggiungere logiche future
  }

  isViewingTicket(ticket: Ticket): boolean {
    return this.viewingTickets.some(v => v.id === ticket.id);
  }
}
