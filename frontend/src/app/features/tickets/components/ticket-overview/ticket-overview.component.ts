import { Component, Input, OnInit } from '@angular/core';
import { Ticket, TicketStatus } from '../../../../shared/models/ticket.model';

@Component({
  selector: 'app-ticket-overview',
  templateUrl: './ticket-overview.component.html',
  styleUrls: ['./ticket-overview.component.scss'], // Corretto da styleUrl a styleUrls
})
export class TicketOverviewComponent implements OnInit {
  @Input() ticket: Ticket | undefined;

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
}
