import { Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { Ticket } from '../../../../shared/models/ticket.model';
import { TicketService } from '../../services/ticket.service';
import { TicketStatus } from '../../../../shared/models/ticket.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  // costruttore
  constructor(private ticketService: TicketService, private router: Router) {}

  // variabili
  tickets: Ticket[] = [];
  TicketStatus = TicketStatus;
  displayedColumns: string[] = ['title', 'status', 'account', 'createdAt'];

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

  @HostListener('window:resize', ['$event'])
  onResize(event: Event): void {
    this.updateDisplayedColumns(); // Aggiorna le colonne al ridimensionamento della finestra
  }

  updateDisplayedColumns(): void {
    if (window.innerWidth < 900) {
      this.displayedColumns = ['title', 'status'];
    } else {
      this.displayedColumns = ['title', 'status', 'account', 'createdAt'];
    }
  }

  addTicketToSidebar(ticket: Ticket): void {
    this.ticketService.addViewingTicket(ticket.id!);
  }

  applyFilter(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target) {
      const filterValue = target.value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
    }
  }
}
