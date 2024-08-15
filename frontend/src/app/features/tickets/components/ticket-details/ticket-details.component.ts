import { Component, OnInit } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { Ticket, TicketStatus } from '../../../../shared/models/ticket.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-ticket-details',
  templateUrl: './ticket-details.component.html',
  styleUrl: './ticket-details.component.scss',
})
export class TicketDetailsComponent implements OnInit {
  // Variables
  ticket!: Ticket;
  id: number | undefined;

  constructor(
    private ticketService: TicketService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.id = +params['id'];
      this.ticketService.getTicketById(this.id).subscribe((data: Ticket) => {
        this.ticket = data;
      });
    });
    console.log('Ticket:', this.ticket);
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
}
