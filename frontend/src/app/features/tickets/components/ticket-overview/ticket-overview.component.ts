import { Component, Input, OnInit } from '@angular/core';
import { Ticket, TicketStatus } from '../../../../shared/models/ticket.model';

@Component({
  selector: 'app-ticket-overview',
  templateUrl: './ticket-overview.component.html',
  styleUrls: ['./ticket-overview.component.scss'], // Corretto da styleUrl a styleUrls
})
export class TicketOverviewComponent {
  @Input() ticket: Ticket | undefined;
}