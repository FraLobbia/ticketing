import { Component, Input, OnInit } from '@angular/core';
import {
  Ticket,
  TicketStatusEnum,
} from '../../../../shared/models/ticket.model';

@Component({
  selector: 'app-ticket-tab-description',
  templateUrl: './ticket-tab-description.component.html',
  styleUrls: ['./ticket-tab-description.component.scss'], // Corretto da styleUrl a styleUrls
})
export class TicketTabDescriptionComponent {
  /**
   * Variabili
   */
  @Input() ticket: Ticket | undefined;
}
