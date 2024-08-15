import { Component, OnInit, ViewChild } from '@angular/core';
import { Ticket } from '../../../../shared/models/ticket.model';
import { TicketService } from '../../services/ticket.service';
import { TicketStatus } from '../../../../shared/models/ticket.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  // costruttore
  constructor(private ticketService: TicketService) {}

  // variabili
  tickets: Ticket[] = [];
  TicketStatus = TicketStatus;
  displayedColumns: string[] = [
    'id',
    'title',
    'status',
    'account',
    'createdAt',
    'actions',
  ];

  dataSource: MatTableDataSource<Ticket> = new MatTableDataSource();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngOnInit(): void {
    this.ticketService.getAllTickets().subscribe((tickets: Ticket[]) => {
      this.tickets = tickets;
      this.dataSource = new MatTableDataSource(tickets);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  getStatusClass(status: TicketStatus): string {
    switch (status) {
      case TicketStatus.OPEN:
        return 'status-open';
      case TicketStatus.IN_PROGRESS:
        return 'status-in-progress';
      case TicketStatus.CLOSED:
        return 'status-closed';
      default:
        return '';
    }
  }

  viewTicket(id: number): void {
    // Implementa la navigazione al dettaglio del ticket
  }
}
